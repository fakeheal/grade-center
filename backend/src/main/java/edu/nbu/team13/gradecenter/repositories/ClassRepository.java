package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class, Long>, CustomizedClassRepository {
    // Additional methods can be defined here if needed
}
