package edu.nbu.team13.gradecenter.seeders;

import edu.nbu.team13.gradecenter.dtos.*;
import edu.nbu.team13.gradecenter.entities.Class;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.Student;
import edu.nbu.team13.gradecenter.entities.Subject;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.services.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SchoolAndSubjectSeeder implements CommandLineRunner {
    private final SchoolService schoolService;

    private final SubjectService subjectService;

    private final UserService userService;

    private final ClassService classService;

    private final StudentService studentService;

    private final TeacherService teacherService;
    private final SchoolYearService schoolYearService;

    public SchoolAndSubjectSeeder(
            SchoolService schoolService,
            SubjectService subjectService,
            UserService userService,
            ClassService classService,
            StudentService studentService,
            TeacherService teacherService,
            SchoolYearService schoolYearService
    ) {
        this.schoolService = schoolService;
        this.subjectService = subjectService;
        this.userService = userService;
        this.classService = classService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.schoolYearService = schoolYearService;
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


        SchoolDto schoolDto2 = new SchoolDto();
        schoolDto2.setName("ПГ по транспорт \"Хенри Форд\" ");
        schoolDto2.setAddress("бул. Цариградско шосе 7, гр. София, България");
        School school2 = schoolService.create(schoolDto2);

        ParentDto parentDto = new ParentDto();
        parentDto.setEmail("ivan.ivanov@parent.com");
        parentDto.setPassword("password123");
        parentDto.setFirstName("Иван");
        parentDto.setLastName("Иванов");
        parentDto.setSchoolId(school2.getId());

        userService.create(parentDto, UserRole.PARENT);

        ClassDto classDto = new ClassDto("A", school.getId(), 10L);
        classDto.setGrade(10L);
        classDto.setSchoolId(school.getId());
        classDto.setName("А");

        Class classEntity = classService.create(classDto);

        ClassDto classDto2 = new ClassDto("A", school.getId(), 11L);
        classDto.setName("B");

        Class classEntity2 = classService.create(classDto2);

        StudentDto studentDto = new StudentDto();
        studentDto.setEmail("georgi.ivanov@parent.com");
        studentDto.setPassword("password321");
        studentDto.setFirstName("Георги");
        studentDto.setLastName("Иванов");
        studentDto.setGrade(10L);
        studentDto.setClassId(classEntity.getId());
        studentDto.setSchoolId(classEntity.getSchoolId());
        Student student = studentService.create(studentDto);

        TeacherDto teacherDto = new TeacherDto();
        List<Subject> teacherSubjects = subjectService.findAllById(Set.of(1L, 2L, 3L));
        teacherDto.setSubjects(new HashSet<>(teacherSubjects));
        teacherDto.setSchoolId(school.getId());
        teacherDto.setEmail("vyara.nesterova@teacher.com");
        teacherDto.setPassword("password321");
        teacherDto.setFirstName("Вяра");
        teacherDto.setLastName("Нестерова");
        TeacherDto teacher = teacherService.create(teacherDto);

        SchoolYearDto schoolYear = new SchoolYearDto();
        schoolYear.setSchoolId(school.getId());
        schoolYear.setYear((short) 2024);
        schoolYear.setTerm((byte) 1);
        schoolYearService.create(schoolYear);

    }
}