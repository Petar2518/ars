package rs.ac.fon.bg.ars.service;

import org.springframework.data.domain.Pageable;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationUnitDomainUpdate;

import java.util.List;

public interface AccommodationUnitService {

    Long save(AccommodationUnitDomain accommodationUnitDomain);

    List<AccommodationUnitDomain> getAll(Long accommodationId, Pageable pageable);

    void deleteById(Long id);

    Long update(AccommodationUnitDomainUpdate accommodationUnitDomainUpdate);

    AccommodationUnitDomain findById(Long id);
}
