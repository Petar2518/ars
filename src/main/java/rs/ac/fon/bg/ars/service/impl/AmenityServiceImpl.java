package rs.ac.fon.bg.ars.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters.AmenityDomainEntityAdapter;
import rs.ac.fon.bg.ars.domain.AmenityDomain;
import rs.ac.fon.bg.ars.exception.specific.AmenityNotFoundException;
import rs.ac.fon.bg.ars.exception.specific.MapperException;
import rs.ac.fon.bg.ars.service.AmenityService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {

    private final AmenityDomainEntityAdapter serviceRepositoryAdapter;
    private final ObjectMapper objectMapper;
    @Override
    public Long save(AmenityDomain amenityDomain) {
        return serviceRepositoryAdapter.save(amenityDomain);
    }

    @Override
    public List<AmenityDomain> getAll(Pageable pageable) {
        return serviceRepositoryAdapter.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        serviceRepositoryAdapter.deleteById(id);
    }

    @Override
    public Long update(AmenityDomain amenityDomain) {
        AmenityDomain existingDomainById = findById(amenityDomain.getId());
        try {
            objectMapper.updateValue(existingDomainById,amenityDomain);
        }catch (JsonMappingException e){
            throw new MapperException();
        }
        return serviceRepositoryAdapter.save(existingDomainById);
    }

    @Override
    public AmenityDomain findById(Long id) {
        return serviceRepositoryAdapter.findById(id).orElseThrow(()->new AmenityNotFoundException(id));
    }
}
