# Use an official Gradle image as the base image
FROM gradle:8.13.0-jdk21 AS build

# Set the working directory
WORKDIR /app

# Copy Gradle wrapper and project files
COPY --chown=gradle:gradle . .

# Install dependencies
RUN gradle dependencies --no-daemon

# Build the application
RUN gradle bootJar --no-daemon

# Use an official OpenJDK image for the runtime
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]