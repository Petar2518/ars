package rs.ac.fon.bg.ars.adapters.controllerServiceAdapters;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDto;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDtoUpdate;
import rs.ac.fon.bg.ars.dto.AddressDto;
import rs.ac.fon.bg.ars.dto.update.AddressDtoUpdate;
import rs.ac.fon.bg.ars.mapper.AddressMapper;
import rs.ac.fon.bg.ars.service.AddressService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressDtoDomainAdapter {
    private final AddressService addressService;
    private final AddressMapper addressMapper;

    public Long save(AddressDto addressDto){
        return addressService.save(addressMapper.dtoToDomain(addressDto));
    }

    public List<AddressDto> getAll(Pageable pageable){
        return addressService.getAll(pageable)
                .stream()
                .map(addressMapper::domainToDto)
                .toList();
    }
    public AddressDto findById(Long id){
        return addressMapper.domainToDto(addressService.findById(id));
    }

    public Long update(AddressDtoUpdate addressDtoUpdate){
        return addressService.update(addressMapper.dtoUpdateToDomainUpdate(addressDtoUpdate));
    }

    public void deleteById(Long id){
        addressService.deleteById(id);
    }

}
