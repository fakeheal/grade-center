package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<User,Long>,
        CustomizedDirectorRepository {

    boolean existsByEmail(String email);

    /** guarantees one director per school */
    boolean existsBySchoolId(Long schoolId);
}
