package edu.nbu.team13.gradecenter.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.nbu.team13.gradecenter.dtos.DirectorDto;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.exceptions.*;
import edu.nbu.team13.gradecenter.repositories.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository repo;


    public User create(DirectorDto dto) {
        if (repo.existsByEmail(dto.getEmail()))
            throw new EmailNotAvailable(dto.getEmail());

        if (repo.existsBySchoolId(dto.getSchoolId()))
            throw new DirectorAlreadyExists(dto.getSchoolId());

        User u = toEntity(dto);
        u.setRole(UserRole.DIRECTOR);          // mark as director
        return repo.save(u);
    }


    public Page<User> index(String firstName,
                            String lastName,
                            String email,
                            Pageable pageable) {
        return repo.findDirectorsByFilters(firstName, lastName, email, pageable);
    }


    public User update(Long id, DirectorDto dto) {
        User u = repo.findById(id)
                .orElseThrow(() -> new UserNotFound(id));

        u.setFirstName(dto.getFirstName());
        u.setLastName(dto.getLastName());
        u.setSchoolId(dto.getSchoolId());

        if (!u.getEmail().equals(dto.getEmail())) {
            if (repo.existsByEmail(dto.getEmail()))
                throw new EmailNotAvailable(dto.getEmail());
            u.setEmail(dto.getEmail());
        }


        return repo.save(u);
    }


    public void delete(Long id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new UserNotFound(id));
        repo.delete(u);
    }

    private User toEntity(DirectorDto d) {
        User u = new User();
        u.setFirstName(d.getFirstName());
        u.setLastName(d.getLastName());
        u.setEmail(d.getEmail());
        u.setSchoolId(d.getSchoolId());
        u.setPassword(d.getPassword());
        return u;
    }
}