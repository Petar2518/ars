package rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.mapper.AccommodationMapper;
import rs.ac.fon.bg.ars.mapper.AccommodationUnitMapper;
import rs.ac.fon.bg.ars.repository.AccommodationRepository;
import rs.ac.fon.bg.ars.repository.AccommodationUnitRepository;


import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccommodationUnitDomainEntityAdapter {

    private final AccommodationUnitMapper accommodationUnitMapper;
    private final AccommodationUnitRepository accommodationUnitRepository;

    public Long save(AccommodationUnitDomain accommodationUnitDomain){
        return accommodationUnitRepository.save(accommodationUnitMapper.domainToEntity(accommodationUnitDomain)).getId();
    }

    public Optional<AccommodationUnitDomain> findById(Long id){
        return accommodationUnitRepository.findById(id)
                .map(accommodationUnitMapper::entityToDomain);
    }

    public void deleteById(Long id){
        accommodationUnitRepository.deleteById(id);
    }

    public List<AccommodationUnitDomain> findAll(Long accommodationId, Pageable pageable){
        return accommodationUnitRepository.findAllByAccommodationId(accommodationId,pageable)
                .getContent()
                .stream()
                .map(accommodationUnitMapper::entityToDomain)
                .toList();
    }

}
