package rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rs.ac.fon.bg.ars.domain.AddressDomain;
import rs.ac.fon.bg.ars.domain.AmenityDomain;
import rs.ac.fon.bg.ars.mapper.AddressMapper;
import rs.ac.fon.bg.ars.mapper.AmenityMapper;
import rs.ac.fon.bg.ars.repository.AddressRepository;
import rs.ac.fon.bg.ars.repository.AmenityRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AmenityDomainEntityAdapter {

    private final AmenityMapper amenityMapper;
    private final AmenityRepository amenityRepository;

    public Long save(AmenityDomain amenityDomain){
        return amenityRepository.save(amenityMapper.domainToEntity(amenityDomain)).getId();
    }

    public Optional<AmenityDomain> findById(Long id){
        return amenityRepository.findById(id)
                .map(amenityMapper::entityToDomain);
    }

    public void deleteById(Long id){
        amenityRepository.deleteById(id);
    }

    public List<AmenityDomain> findAll(Pageable pageable){
        return amenityRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(amenityMapper::entityToDomain)
                .toList();
    }

}