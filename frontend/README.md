# Grade Center

A modern web-based school grade book for teachers, students, and parents. Effortless grade management,
real-time performance insights, and secure access for everyone.

## Features

- 🚀 Everything you need to manage grading of your students in one place
- 👤 Role-Based Dashboards
  - Teachers: Manage grades, assignments, and student performance
  - Students: View grades, assignments, and progress
  - Parents: Monitor student performance and communicate with teachers
- 🎖️ Easy Grade Management
- 📊 Real-Time Performance Insights

## Getting Started

### Installation

Install the dependencies:

```bash
npm install
```

### Development

Start the development server with HMR:

```bash
npm run dev
```

Your application will be available at `http://localhost:5173`.

## Building for Production

Create a production build:

```bash
npm run build
```

## Deployment

### Docker Deployment

To build and run using Docker:

```bash
docker build -t my-app .

# Run the container
docker run -p 3000:3000 my-app
```

### DIY Deployment

If you're familiar with deploying Node applications, the built-in app server is production-ready.

Make sure to deploy the output of `npm run build`

```
├── package.json
├── package-lock.json (or pnpm-lock.yaml, or bun.lockb)
├── build/
│   ├── client/    # Static assets
│   └── server/    # Server-side code
```

## Styling

This template comes with [Tailwind CSS](https://tailwindcss.com/) already configured for a simple default starting experience. For additional bling, DaisyUI was added as a plugin to Tailwind CSS.

---

Built with ❤️ using React Router, Spring Boot.
