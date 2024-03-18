package rs.ac.fon.bg.ars.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters.AccommodationUnitDomainEntityAdapter;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationUnitDomainUpdate;
import rs.ac.fon.bg.ars.exception.specific.AccommodationUnitNotFoundException;
import rs.ac.fon.bg.ars.exception.specific.MapperException;
import rs.ac.fon.bg.ars.service.AccommodationUnitService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationUnitServiceImpl implements AccommodationUnitService {

    private final AccommodationUnitDomainEntityAdapter serviceRepositoryAdapter;
    private final ObjectMapper objectMapper;
    @Override
    public Long save(AccommodationUnitDomain accommodationUnitDomain) {
        return serviceRepositoryAdapter.save(accommodationUnitDomain);
    }

    @Override
    public List<AccommodationUnitDomain> getAll(Long accommodationId, Pageable pageable) {
        return serviceRepositoryAdapter.findAll(accommodationId,pageable);
    }

    @Override
    public void deleteById(Long id) {
        serviceRepositoryAdapter.deleteById(id);
    }

    @Override
    public Long update(AccommodationUnitDomainUpdate accommodationUnitDomainUpdate) {
        AccommodationUnitDomain oldAccommodationUnit = findById(accommodationUnitDomainUpdate.getId());
        try{
            objectMapper.updateValue(oldAccommodationUnit,accommodationUnitDomainUpdate);
        }catch (JsonMappingException e){
            throw new MapperException();
        }
        return serviceRepositoryAdapter.save(oldAccommodationUnit);
    }

    @Override
    public AccommodationUnitDomain findById(Long id) {
        return serviceRepositoryAdapter.findById(id).orElseThrow(()-> new AccommodationUnitNotFoundException(id));
    }
}
