package edu.nbu.team13.gradecenter.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.nbu.team13.gradecenter.entities.User;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Long schoolId;

    @Nullable
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.schoolId = user.getSchool().getId();
    }
}