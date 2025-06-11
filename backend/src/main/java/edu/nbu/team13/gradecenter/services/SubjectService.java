package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.SubjectDto;
import edu.nbu.team13.gradecenter.entities.Subject;
import edu.nbu.team13.gradecenter.exceptions.SubjectNotFound;
import edu.nbu.team13.gradecenter.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    private final SchoolService schoolService;

    public SubjectService(SubjectRepository subjectRepository, SchoolService schoolService) {
        this.subjectRepository = subjectRepository;
        this.schoolService = schoolService;
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

        subject.setSchool(this.schoolService.findById(subjectDto.getSchoolId()));
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

        existingSubject.setSchool(this.schoolService.findById(subjectDto.getSchoolId()));
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
                .orElseThrow(() -> new SubjectNotFound(id));
    }

    /**
     * Finds all subjects by a set of subject IDs.
     *
     * @param subjectIds the set of subject IDs
     * @return a list of subjects
     */
    public List<Subject> findAllById(Set<Long> subjectIds) {
        return subjectRepository.findAllById(subjectIds);
    }
}
