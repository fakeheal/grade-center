package edu.nbu.team13.gradecenter.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("test")
public class TeacherRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TeacherRepository teacherRepository;

}
