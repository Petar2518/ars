package rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.AddressDomain;
import rs.ac.fon.bg.ars.mapper.AccommodationUnitMapper;
import rs.ac.fon.bg.ars.mapper.AddressMapper;
import rs.ac.fon.bg.ars.repository.AccommodationUnitRepository;
import rs.ac.fon.bg.ars.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddressDomainEntityAdapter {

    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    public Long save(AddressDomain addressDomain){
        return addressRepository.save(addressMapper.domainToEntity(addressDomain)).getId();
    }

    public Optional<AddressDomain> findById(Long id){
        return addressRepository.findById(id)
                .map(addressMapper::entityToDomain);
    }

    public void deleteById(Long id){
        addressRepository.deleteById(id);
    }

    public List<AddressDomain> findAll(Pageable pageable){
        return addressRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(addressMapper::entityToDomain)
                .toList();
    }

}
