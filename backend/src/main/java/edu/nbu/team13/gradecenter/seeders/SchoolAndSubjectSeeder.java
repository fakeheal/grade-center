package edu.nbu.team13.gradecenter.seeders;

import edu.nbu.team13.gradecenter.dtos.SchoolDto;
import edu.nbu.team13.gradecenter.dtos.SubjectDto;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.services.SchoolService;
import edu.nbu.team13.gradecenter.services.SubjectService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SchoolAndSubjectSeeder implements CommandLineRunner {
    private final SchoolService schoolService;

    private final SubjectService subjectService;

    public SchoolAndSubjectSeeder(SchoolService schoolService, SubjectService subjectService) {
        this.schoolService = schoolService;
        this.subjectService = subjectService;
    }

    @Override
    @Transactional
    public void run(String... args) {
        SchoolDto schoolDto = new SchoolDto();

        schoolDto.setName("ОУ \"Св. св. Кирил и Методий\" ");
        schoolDto.setAddress("бул. Черни Връх 25, гр. София, България");

        School school = schoolService.create(schoolDto);

        List<String> subjectNames = List.of("Математика", "История и цивилизация", "География и икономика",
                "Физическо възпитание и спорт", "Изобразително изкуство",
                "Философия", "Биология и здравно образование", "Химия и опазване на околната среда",
                "Физика и астрономия", "Английски език", "Немски език", "Френски език"
        );

        for (String subjectName : subjectNames) {
            SubjectDto subjectDto = new SubjectDto();
            subjectDto.setName(subjectName);
            subjectDto.setSchoolId(school.getId());
            subjectService.create(subjectDto);
        }
    }
}