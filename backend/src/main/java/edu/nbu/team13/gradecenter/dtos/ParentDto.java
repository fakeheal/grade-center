package edu.nbu.team13.gradecenter.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ParentDto {

    private Long id;

    private String firstName;
    private String lastName;

    private String email;
    private String password;

    @JsonProperty("school_id")           // accept both camelCase & snake_case
    private Long schoolId;

    /* list of student IDs that this user is parent of */
    @JsonProperty("student_ids")
    private List<Long> studentIds;
}
