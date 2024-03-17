package rs.ac.fon.bg.ars.adapters.controllerServiceAdapters;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rs.ac.fon.bg.ars.dto.AccommodationDto;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;
import rs.ac.fon.bg.ars.mapper.AccommodationMapper;
import rs.ac.fon.bg.ars.service.AccommodationService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccommodationDtoDomainAdapter {
    private final AccommodationService accService;
    private final AccommodationMapper accMapper;

    public Long save(AccommodationDto accommodationDto){
        return accService.save(accMapper.dtoToDomain(accommodationDto));
    }

    public List<AccommodationDto> getAll(Pageable pageable){
        return accService.getAll(pageable)
                .stream()
                .map(accMapper::domainToDto)
                .toList();
    }
    public AccommodationDto findById(Long id){
        return accMapper.domainToDto(accService.findById(id));
    }

    public Long update(AccommodationDtoUpdate accommodationDtoUpdate){
        return accService.update(accMapper.dtoUpdateToDomainUpdate(accommodationDtoUpdate));
    }

    public void deleteById(Long id){
        accService.deleteById(id);
    }

    public List<AccommodationDto> getAllByHost(Long hostId, Pageable pageable){
        return accService.getAllByHost(hostId,pageable)
                .stream()
                .map(accMapper::domainToDto)
                .toList();
    }
}
