package edu.nbu.team13.gradecenter.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import edu.nbu.team13.gradecenter.dtos.UserDto;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
@Table(name = "users")
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Getter
    @Setter
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Getter
    @Setter
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Setter
    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Getter
    @Setter
    @ManyToOne
    private School school;

    @CreationTimestamp
    @Column(name = "created_on", nullable = false, updatable = false)
    private Instant createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on", nullable = false)
    private Instant updatedOn;

    public User() {}
    public void FromDto(UserDto dto){
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
    }
}
