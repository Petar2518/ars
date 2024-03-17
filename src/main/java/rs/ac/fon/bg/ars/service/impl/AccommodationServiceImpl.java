package rs.ac.fon.bg.ars.service.impl;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters.AccommodationDomainEntityAdapter;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.exception.specific.AccommodationNotFoundException;
import rs.ac.fon.bg.ars.exception.specific.MapperException;
import rs.ac.fon.bg.ars.service.AccommodationService;


import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationDomainEntityAdapter serviceRepositoryAdapter;

    private final ObjectMapper objectMapper;

    @Override
    public Long save(AccommodationDomain accommodationDomain) {
        return serviceRepositoryAdapter.save(accommodationDomain);
    }

    @Override
    public List<AccommodationDomain> getAll(Pageable pageable) {
        return serviceRepositoryAdapter.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        serviceRepositoryAdapter.deleteById(id);
    }

    @Override
    public Long update(AccommodationDomainUpdate accommodationDomainUpdate) {

        AccommodationDomain existingAccById = findById(accommodationDomainUpdate.getId());
        try{
            objectMapper.updateValue(existingAccById,accommodationDomainUpdate);
        }catch (JsonMappingException e){
            throw new MapperException();
        }
        return serviceRepositoryAdapter.save(existingAccById);
    }

    @Override
    public AccommodationDomain findById(Long id) {
        return serviceRepositoryAdapter.findById(id).orElseThrow(()-> new AccommodationNotFoundException(id));
    }

    @Override
    public List<AccommodationDomain> getAllByHost(Long hostId, Pageable pageable) {
        return serviceRepositoryAdapter.findAllByHostId(hostId,pageable);
    }
}
