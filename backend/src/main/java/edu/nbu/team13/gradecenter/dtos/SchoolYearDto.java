package edu.nbu.team13.gradecenter.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolYearDto {
    private Long id;
    private short year;
    private byte term;
    private Long schoolId;
}
