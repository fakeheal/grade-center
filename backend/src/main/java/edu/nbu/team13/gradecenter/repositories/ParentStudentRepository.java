package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.ParentStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentStudentRepository extends JpaRepository<ParentStudent, Long> {
    /**
     * Deletes all links associated with a given parent ID.
     *
     * @param parentId the ID of the parent whose links are to be deleted
     */
    void deleteAllByParentId(Long parentId);
}