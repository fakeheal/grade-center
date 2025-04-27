package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.ClassDto;
import edu.nbu.team13.gradecenter.dtos.SchoolDto;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.Class;
import edu.nbu.team13.gradecenter.repositories.ClassRepository;
import edu.nbu.team13.gradecenter.repositories.SchoolRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClassService {
    private final ClassRepository classRepository;

    public ClassService(ClassRepository ClassRepository) {
        this.classRepository = ClassRepository;
    }
    public Class create(ClassDto classDto) {
        // Convert ClassDto to Class entity
        Class cls = new Class();
        cls.setName(classDto.getName());
        cls.setGrade(classDto.getGrade());
        cls.setSchoolId(classDto.getSchoolId());


        // Save the Class entity to the database
        return classRepository.save(cls);
    }
    public Class update(Long id, ClassDto classDto) {
        // Find the existing Class by ID
        Class existingClass = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        // Update the Class's properties
        existingClass.setName(classDto.getName());
        existingClass.setGrade(classDto.getGrade());
        existingClass.setSchoolId(classDto.getSchoolId());

        // Save the updated Class entity to the database
        return classRepository.save(existingClass);
    }
    public void delete(Long id) {
        // Find the existing Class by ID
        Class existingClass = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        // Delete the Class entity from the database
        classRepository.delete(existingClass);
    }
    public Class findById(Long id) {
        // Find the existing Class by ID
        return classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));
    }
    public Page<Class> search(String name, Long grade, Long schoolId, Pageable pageable) {
        return classRepository.findByOptionalFilters(name,grade,schoolId, pageable);
    }
}
