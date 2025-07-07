package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Subject;
import edu.nbu.team13.gradecenter.repositories.views.SubjectTeacherView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAllBySchoolId(Long schoolId);

    @Query(value = """
        SELECT subjects.id AS subject_id,
               subjects.name AS subject_name,
               teachers.id AS teacher_id,
               CONCAT(users.first_name, ' ', users.last_name) AS teacher_name
        FROM subjects
        JOIN teacher_subject ON teacher_subject.subject_id = subjects.id
        JOIN teachers ON teachers.id = teacher_subject.teacher_id
        JOIN users ON users.id = teachers.user_id
        WHERE subjects.school_id = :schoolId
    """, nativeQuery = true)
    List<SubjectTeacherView> findAllWithTeachers(Long schoolId);
}
