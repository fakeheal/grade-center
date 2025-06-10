package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long>, CustomizedSchoolRepository {

    /**
     * Checks if a school already has a user with role.
     *
     * @param role the name of the role to check
     * @param id   the ID of the school to check
     * @return true if a user with the specified role exists in the school, false otherwise
     */
    boolean existsByUsersRoleAndId(UserRole role, Long id);

}
