package edu.nbu.team13.gradecenter.configurations;

import edu.nbu.team13.gradecenter.dtos.TeacherDto;
import edu.nbu.team13.gradecenter.entities.Teacher;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper
                .typeMap(Teacher.class, TeacherDto.class)
                .addMappings(
                        mapping -> {
                            mapping.map(src -> src.getUser().getFirstName(), TeacherDto::setFirstName);
                            mapping.map(src -> src.getUser().getLastName(), TeacherDto::setLastName);
                            mapping.map(src -> src.getUser().getEmail(), TeacherDto::setEmail);
                            mapping.map(src -> src.getUser().getPassword(), TeacherDto::setPassword);
                            mapping.map(src -> src.getUser().getSchool().getId(), TeacherDto::setSchoolId);
                            mapping.map(src -> src.getUser().getId(), TeacherDto::setUserId);
                        }
                );

        return modelMapper;
    }
}