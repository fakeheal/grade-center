package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.SubjectDto;
import edu.nbu.team13.gradecenter.entities.Subject;
import edu.nbu.team13.gradecenter.exceptions.SubjectNotFound;
import edu.nbu.team13.gradecenter.exceptions.TeacherNotFound;
import edu.nbu.team13.gradecenter.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    /**
     * Finds all subjects for a given school ID.
     *
     * @param schoolId the ID of the school
     * @return a list of subjects
     */
    public List<Subject> findAll(Long schoolId) {
        return subjectRepository.findAllBySchoolId(schoolId);
    }


    /**
     * Creates a new subject.
     *
     * @param subjectDto the subject DTO
     * @return the created subject
     */
    public Subject create(SubjectDto subjectDto) {
        Subject subject = new Subject();

        subject.setSchoolId(subjectDto.getSchoolId());
        subject.setName(subjectDto.getName());

        return subjectRepository.save(subject);
    }

    /**
     * Updates an existing subject.
     *
     * @param id         the ID of the subject to update
     * @param subjectDto the subject DTO with updated values
     * @return the updated subject
     */
    public Subject update(Long id, SubjectDto subjectDto) {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFound(id));

        existingSubject.setSchoolId(subjectDto.getSchoolId());
        existingSubject.setName(subjectDto.getName());

        return subjectRepository.save(existingSubject);
    }

    /**
     * Deletes a subject by its ID.
     *
     * @param id the ID of the subject to delete
     */
    public void delete(Long id) {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFound(id));

        subjectRepository.delete(existingSubject);
    }

    /**
     * Finds a subject by its ID.
     *
     * @param id the ID of the subject
     * @return the found subject
     */
    public Subject findById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFound(id));
    }
}
