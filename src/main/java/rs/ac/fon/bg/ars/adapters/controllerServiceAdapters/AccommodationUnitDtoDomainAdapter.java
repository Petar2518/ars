package rs.ac.fon.bg.ars.adapters.controllerServiceAdapters;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDto;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDtoUpdate;
import rs.ac.fon.bg.ars.mapper.AccommodationUnitMapper;
import rs.ac.fon.bg.ars.service.AccommodationUnitService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccommodationUnitDtoDomainAdapter {
    private final AccommodationUnitService accommodationUnitService;
    private final AccommodationUnitMapper accommodationUnitMapper;

    public Long save(AccommodationUnitDto accommodationUnitDto){
        return accommodationUnitService.save(accommodationUnitMapper.dtoToDomain(accommodationUnitDto));
    }

    public List<AccommodationUnitDto> getAll(Long accommodationId,Pageable pageable){
        return accommodationUnitService.getAll(accommodationId,pageable)
                .stream()
                .map(accommodationUnitMapper::domainToDto)
                .toList();
    }
    public AccommodationUnitDto findById(Long id){
        return accommodationUnitMapper.domainToDto(accommodationUnitService.findById(id));
    }

    public Long update(AccommodationUnitDtoUpdate accommodationUnitDtoUpdate){
        return accommodationUnitService.update(accommodationUnitMapper.dtoUpdateToDomainUpdate(accommodationUnitDtoUpdate));
    }

    public void deleteById(Long id){
        accommodationUnitService.deleteById(id);
    }

}
