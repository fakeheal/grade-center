package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.SchoolYearDto;
import edu.nbu.team13.gradecenter.entities.SchoolYear;
import edu.nbu.team13.gradecenter.exceptions.*;
import edu.nbu.team13.gradecenter.repositories.SchoolYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolYearService {

    private final SchoolYearRepository repo;

    /* -------- CREATE -------- */
    public SchoolYear create(SchoolYearDto dto) {
        validate(dto);

        if (repo.existsByYearAndTermAndSchoolId(
                dto.getYear(), dto.getTerm(), dto.getSchoolId()))
            throw new SchoolYearAlreadyExists(dto.getYear(), dto.getTerm(), dto.getSchoolId());

        return repo.save(toEntity(dto));
    }

    /* -------- READ ---------- */
    public Page<SchoolYear> index(Short year, Byte term,
                                  Long schoolId, Pageable pg) {
        return repo.findByFilters(year, term, schoolId, pg);
    }

    /* -------- UPDATE -------- */
    public SchoolYear update(Long id, SchoolYearDto dto) {
        validate(dto);

        SchoolYear sy = repo.findById(id).orElseThrow(() -> new SchoolYearNotFound(id));

        boolean changingKey =
                sy.getYear()     != dto.getYear()  ||
                        sy.getTerm()     != dto.getTerm()  ||
                        !sy.getSchoolId().equals(dto.getSchoolId());

        if (changingKey &&
                repo.existsByYearAndTermAndSchoolId(
                        dto.getYear(), dto.getTerm(), dto.getSchoolId()))
            throw new SchoolYearAlreadyExists(dto.getYear(), dto.getTerm(), dto.getSchoolId());

        sy.setYear(dto.getYear());
        sy.setTerm(dto.getTerm());
        sy.setSchoolId(dto.getSchoolId());
        return repo.save(sy);
    }

    /* -------- DELETE -------- */
    public void delete(Long id) {
        repo.delete(repo.findById(id).orElseThrow(() -> new SchoolYearNotFound(id)));
    }

    private void validate(SchoolYearDto d) {

        if (d.getYear() < 1900 || d.getYear() > 2100)
            throw new InvalidInput("Year must be between 1900 and 2100");

        if (d.getTerm() != 1 && d.getTerm() != 2)
            throw new InvalidInput("Term must be 1 or 2");

        if (d.getSchoolId() == null)
            throw new InvalidInput("school_id may not be null");
    }

    /* -------- helper -------- */
    private SchoolYear toEntity(SchoolYearDto d) {
        SchoolYear sy = new SchoolYear();
        sy.setYear(d.getYear());
        sy.setTerm(d.getTerm());
        sy.setSchoolId(d.getSchoolId());
        return sy;
    }
}
