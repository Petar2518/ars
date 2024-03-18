package rs.ac.fon.bg.ars.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.fon.bg.ars.adapters.controllerServiceAdapters.AccommodationDtoDomainAdapter;
import rs.ac.fon.bg.ars.adapters.controllerServiceAdapters.AccommodationUnitDtoDomainAdapter;
import rs.ac.fon.bg.ars.dto.AccommodationDto;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDto;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDtoUpdate;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccommodationUnitController {

    private final AccommodationUnitDtoDomainAdapter accUnitAdapter;

    @GetMapping("accommodations/{accommodationId}/rooms")
    public List<AccommodationUnitDto> getAllByPage(
            @PathVariable Long accommodationId,
            @PageableDefault(sort="id",page=0,size=10,direction = Sort.Direction.ASC) Pageable pageable){
        return accUnitAdapter.getAll(accommodationId,pageable);
    }

    @GetMapping("/rooms/{id}")
    public AccommodationUnitDto findById(@PathVariable Long id){
        return accUnitAdapter.findById(id);
    }

    @PostMapping("/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public Long save(@Valid @RequestBody AccommodationUnitDto accommodationUnitDto){
        return accUnitAdapter.save(accommodationUnitDto);
    }

    @PutMapping("/rooms")
    public Long updateAccommodation(@Valid @RequestBody AccommodationUnitDtoUpdate accommodationUnitDtoUpdate){
        return accUnitAdapter.update(accommodationUnitDtoUpdate);
    }

    @DeleteMapping("/rooms/{id}")
    public void delete(@PathVariable Long id){
        accUnitAdapter.deleteById(id);
    }

}
