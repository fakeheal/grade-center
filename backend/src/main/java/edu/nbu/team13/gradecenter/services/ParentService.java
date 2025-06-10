package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.ParentDto;
import edu.nbu.team13.gradecenter.entities.*;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.exceptions.*;
import edu.nbu.team13.gradecenter.repositories.ParentRepository;
import edu.nbu.team13.gradecenter.repositories.ParentStudentRepository;
import edu.nbu.team13.gradecenter.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository parentRepo;
    private final ParentStudentRepository linkRepo;
    private final UserRepository          userRepo;


    public User create(ParentDto dto) {
        if (parentRepo.existsByEmail(dto.getEmail()))
            throw new EmailNotAvailable(dto.getEmail());

        User parent = toEntity(dto);
        parent.setRole(UserRole.PARENT);

        User savedParent = parentRepo.save(parent);


        saveLinks(savedParent, dto.getStudentIds());

        return savedParent;
    }


    public Page<User> index(String fn, String ln, String email, Pageable pg) {
        return parentRepo.findParentsByFilters(fn, ln, email, pg);
    }


    public User update(Long id, ParentDto dto) {
        User p = parentRepo.findById(id).orElseThrow(() -> new UserNotFound(id));

        p.setFirstName(dto.getFirstName());
        p.setLastName(dto.getLastName());
        p.setSchoolId(dto.getSchoolId());

        if (!p.getEmail().equals(dto.getEmail())) {
            if (parentRepo.existsByEmail(dto.getEmail()))
                throw new EmailNotAvailable(dto.getEmail());
            p.setEmail(dto.getEmail());
        }

        User saved = parentRepo.save(p);


        linkRepo.deleteAll(linkRepo.findAll()
                .stream()
                .filter(l -> l.getParent().getId().equals(id))
                .toList());

        saveLinks(saved, dto.getStudentIds());
        return saved;
    }


    public void delete(Long id) {
        parentRepo.delete(parentRepo.findById(id)
                .orElseThrow(() -> new UserNotFound(id)));
    }


    private User toEntity(ParentDto d) {
        User u = new User();
        u.setFirstName(d.getFirstName());
        u.setLastName(d.getLastName());
        u.setEmail(d.getEmail());
        u.setPassword(d.getPassword());
        u.setSchoolId(d.getSchoolId());
        return u;
    }

    private void saveLinks(User parent, List<Long> studentIds) {
        if (studentIds == null) return;
        for (Long sid : studentIds) {
            User s = userRepo.findById(sid).orElseThrow(() -> new StudentNotFound(sid));
            if (!s.getRole().equals(UserRole.STUDENT))
                throw new IllegalArgumentException("User " + sid + " is not a student");

            ParentStudent link = new ParentStudent();
            link.setParent(parent);
            link.setStudent(s);
            linkRepo.save(link);
        }
    }

    public User findById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
