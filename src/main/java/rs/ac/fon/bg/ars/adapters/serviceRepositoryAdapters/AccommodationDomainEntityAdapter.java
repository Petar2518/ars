package rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.mapper.AccommodationMapper;
import rs.ac.fon.bg.ars.repository.AccommodationRepository;


import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccommodationDomainEntityAdapter {

    private final AccommodationMapper accommodationMapper;
    private final AccommodationRepository accommodationRepository;

    public Long save(AccommodationDomain accommodationDomain){
        return accommodationRepository.save(accommodationMapper.domainToEntity(accommodationDomain)).getId();
    }

    public Optional<AccommodationDomain> findById(Long id){
        return accommodationRepository.findById(id)
                .map(accommodationMapper::entityToDomain);
    }

    public void deleteById(Long id){
        accommodationRepository.deleteById(id);
    }

    public List<AccommodationDomain> findAll(Pageable pageable){
        return accommodationRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(accommodationMapper::entityToDomain)
                .toList();
    }

    public List<AccommodationDomain> findAllByHostId(Long hostId, Pageable pageable){
        return accommodationRepository.findAllByHostId(hostId, pageable).getContent()
                .stream()
                .map(accommodationMapper::entityToDomain)
                .toList();
    }
}
