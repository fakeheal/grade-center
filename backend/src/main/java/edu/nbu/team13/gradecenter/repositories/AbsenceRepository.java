package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository extends CustomizedAbsenceRepository, JpaRepository<Absence, Long> {
    // Additional query methods can be defined here if needed
}
