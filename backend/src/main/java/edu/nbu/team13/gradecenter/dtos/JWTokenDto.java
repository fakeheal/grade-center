package edu.nbu.team13.gradecenter.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTokenDto {
    private String token;

    public JWTokenDto(String token) {
        this.token = token;
    }
}
