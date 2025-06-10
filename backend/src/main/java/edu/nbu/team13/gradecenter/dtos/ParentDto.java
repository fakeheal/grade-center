package edu.nbu.team13.gradecenter.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParentDto {

    private Long id;

    private String firstName;
    private String lastName;

    private String email;
    private String password;

    private Long schoolId;

    private List<Long> studentIds;
}
