package edu.nbu.team13.gradecenter.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SchoolYearDto {

    private Long id;


    private short year;


    private byte term;

    @NotNull
    @JsonProperty("school_id")
    private Long schoolId;
}
