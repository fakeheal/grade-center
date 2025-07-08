package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
}