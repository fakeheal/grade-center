package edu.nbu.team13.gradecenter.dtos;

import edu.nbu.team13.gradecenter.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParentResponseDto extends UserDto {
    private List<Long> studentIds;

    public ParentResponseDto(User user, List<Long> studentIds) {
        super(user);
        this.studentIds = studentIds;
    }
}
