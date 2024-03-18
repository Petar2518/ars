package rs.ac.fon.bg.ars.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters.AddressDomainEntityAdapter;
import rs.ac.fon.bg.ars.domain.AddressDomain;
import rs.ac.fon.bg.ars.domain.update.AddressDomainUpdate;
import rs.ac.fon.bg.ars.exception.specific.AddressNotFoundException;
import rs.ac.fon.bg.ars.exception.specific.MapperException;
import rs.ac.fon.bg.ars.service.AddressService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressDomainEntityAdapter serviceRepositoryAdapter;
    private final ObjectMapper objectMapper;
    @Override
    public Long save(AddressDomain addressDomain) {
        return serviceRepositoryAdapter.save(addressDomain);
    }

    @Override
    public List<AddressDomain> getAll(Pageable pageable) {
        return serviceRepositoryAdapter.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        serviceRepositoryAdapter.deleteById(id);
    }

    @Override
    public Long update(AddressDomainUpdate addressDomainUpdate) {
        AddressDomain existingAddressById = findById(addressDomainUpdate.getId());
        try{
            objectMapper.updateValue(existingAddressById,addressDomainUpdate);
        }catch (JsonMappingException e){
            throw new MapperException();
        }
        return serviceRepositoryAdapter.save(existingAddressById);
    }

    @Override
    public AddressDomain findById(Long id) {
        return serviceRepositoryAdapter.findById(id).orElseThrow(()-> new AddressNotFoundException(id));
    }
}
