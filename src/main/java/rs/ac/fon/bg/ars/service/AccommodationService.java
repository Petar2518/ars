package rs.ac.fon.bg.ars.service;

import org.springframework.data.domain.Pageable;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.dto.AccommodationDto;

import java.util.List;

public interface AccommodationService {

    Long save(AccommodationDomain accommodationDomain);

    List<AccommodationDomain> getAll(Pageable pageable);

    void deleteById(Long id);

    Long update(AccommodationDomainUpdate accommodationDomainUpdate);

    AccommodationDomain findById(Long id);

    List<AccommodationDomain> getAllByHost(Long hostId, Pageable pageable);
}
