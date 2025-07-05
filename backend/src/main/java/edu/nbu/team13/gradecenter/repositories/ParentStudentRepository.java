package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.ParentStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ParentStudentRepository extends JpaRepository<ParentStudent, Long> {
    /**
     * Deletes all links associated with a given parent ID.
     *
     * @param parentId the ID of the parent whose links are to be deleted
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM ParentStudent ps WHERE ps.parent.id = :parentId")
    void deleteAllByParentId(Long parentId);

    /**
     * Finds all ParentStudent links for a given parent ID.
     *
     * @param parentId the ID of the parent
     * @return a list of ParentStudent entities
     */
    @Query("SELECT ps FROM ParentStudent ps JOIN FETCH ps.student WHERE ps.parent.id = :parentId")
    List<ParentStudent> findAllByParentId(Long parentId);
}
