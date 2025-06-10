package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.SchoolDto;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.repositories.SchoolRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public School create(SchoolDto schoolDto) {
        // Convert SchoolDto to School entity
        School school = new School();
        school.setName(schoolDto.getName());
        school.setAddress(schoolDto.getAddress());

        // Save the school entity to the database
        return schoolRepository.save(school);
    }

    public School update(Long id, SchoolDto schoolDto) {
        // Find the existing school by ID
        School existingSchool = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));

        // Update the school's properties
        existingSchool.setName(schoolDto.getName());
        existingSchool.setAddress(schoolDto.getAddress());

        // Save the updated school entity to the database
        return schoolRepository.save(existingSchool);
    }

    public void delete(Long id) {
        // Find the existing school by ID
        School existingSchool = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));

        // Delete the school entity from the database
        schoolRepository.delete(existingSchool);
    }

    public School findById(Long id) {
        // Find the existing school by ID
        return schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));
    }

    public Page<School> search(String name, String address, Pageable pageable) {
        return schoolRepository.findByOptionalFilters(name, address, pageable);
    }

    public Boolean hasDirector(Long schoolId) {
        return schoolRepository.existsByUsersRoleAndId(UserRole.DIRECTOR, schoolId);
    }
}
