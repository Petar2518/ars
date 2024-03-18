package rs.ac.fon.bg.ars.service;

import org.springframework.data.domain.Pageable;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.AddressDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationUnitDomainUpdate;
import rs.ac.fon.bg.ars.domain.update.AddressDomainUpdate;

import java.util.List;

public interface AddressService {
    Long save(AddressDomain addressDomain);

    List<AddressDomain> getAll(Pageable pageable);

    void deleteById(Long id);

    Long update(AddressDomainUpdate addressDomainUpdate);

    AddressDomain findById(Long id);
}
