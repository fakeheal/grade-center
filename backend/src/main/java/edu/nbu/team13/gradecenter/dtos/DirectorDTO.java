package edu.nbu.team13.gradecenter.dtos;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DirectorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long schoolId;           // guarantee the link to a school
}
