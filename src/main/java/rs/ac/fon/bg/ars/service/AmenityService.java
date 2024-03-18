package rs.ac.fon.bg.ars.service;

import org.springframework.data.domain.Pageable;
import rs.ac.fon.bg.ars.domain.AddressDomain;
import rs.ac.fon.bg.ars.domain.AmenityDomain;
import rs.ac.fon.bg.ars.domain.update.AddressDomainUpdate;

import java.util.List;

public interface AmenityService {
    Long save(AmenityDomain amenityDomain);

    List<AmenityDomain> getAll(Pageable pageable);

    void deleteById(Long id);

    Long update(AmenityDomain amenityDomain);

    AmenityDomain findById(Long id);
}
