package edu.nbu.team13.gradecenter.seeders;

import edu.nbu.team13.gradecenter.dtos.SchoolDto;
import edu.nbu.team13.gradecenter.services.SchoolService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SchoolSeeder implements CommandLineRunner {
    private final SchoolService schoolService;

    public SchoolSeeder(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @Override
    @Transactional
    public void run(String... args) {
        SchoolDto schoolDto = new SchoolDto();

        schoolDto.setName("ОУ \"Св. св. Кирил и Методий\" ");
        schoolDto.setAddress("бул. Черни Връх 25, гр. София, България");

        schoolService.create(schoolDto);
    }
}