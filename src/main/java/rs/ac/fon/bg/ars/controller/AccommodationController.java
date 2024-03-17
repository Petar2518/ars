package rs.ac.fon.bg.ars.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.fon.bg.ars.adapters.controllerServiceAdapters.AccommodationDtoDomainAdapter;
import rs.ac.fon.bg.ars.dto.AccommodationDto;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;

import java.util.List;

@RestController
@RequestMapping("accommodations")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationDtoDomainAdapter accAdapter;

    @GetMapping
    public List<AccommodationDto> getAllByPage(@PageableDefault(sort="id",page=0,size=10,direction = Sort.Direction.ASC) Pageable pageable){
        return accAdapter.getAll(pageable);
    }

    @GetMapping("/{id}")
    public AccommodationDto findById(@PathVariable Long id){
        return accAdapter.findById(id);
    }

    @GetMapping("/hosts/{hostId}")
    public List<AccommodationDto> getAllForHostByPage(
            @PageableDefault
                    (sort = "id",
                    page=0,
                    size=10,
                    direction = Sort.Direction.ASC)
            Pageable pageable,
            @PathVariable Long hostId){
        return accAdapter.getAllByHost(hostId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long save(@Valid @RequestBody AccommodationDto accommodationDto){
        return accAdapter.save(accommodationDto);
    }

    @PutMapping
    public Long updateAccommodation(@Valid @RequestBody AccommodationDtoUpdate accommodationUpdate){
        return accAdapter.update(accommodationUpdate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        accAdapter.deleteById(id);
    }

}
