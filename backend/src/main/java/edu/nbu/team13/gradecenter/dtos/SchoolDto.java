package edu.nbu.team13.gradecenter.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SchoolDto {
    private String name;
    private String address;

    public SchoolDto(){
    }

    public SchoolDto(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
