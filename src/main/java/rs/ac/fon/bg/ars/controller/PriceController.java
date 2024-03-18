package rs.ac.fon.bg.ars.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.fon.bg.ars.adapters.controllerServiceAdapters.AmenityDtoDomainAdapter;
import rs.ac.fon.bg.ars.adapters.controllerServiceAdapters.PriceDtoDomainAdapter;
import rs.ac.fon.bg.ars.dto.AmenityDto;
import rs.ac.fon.bg.ars.dto.PriceDto;
import rs.ac.fon.bg.ars.dto.dateFilter.PricesDate;
import rs.ac.fon.bg.ars.dto.update.PriceDtoUpdate;

import java.util.List;

@RestController
@RequestMapping("prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceDtoDomainAdapter priceAdapter;

    @GetMapping("{accommodationId}/price")
    public List<PriceDto> getAllByPage(
            @PathVariable Long accommodationId,
            PricesDate pricesDate,
            @PageableDefault(sort="id",page=0,size=10,direction = Sort.Direction.ASC) Pageable pageable){
        return priceAdapter.getAll(accommodationId,pricesDate,pageable);
    }

    @GetMapping("/{id}")
    public PriceDto findById(@PathVariable Long id){
        return priceAdapter.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long save(@Valid @RequestBody PriceDto priceDto){
        return priceAdapter.save(priceDto);
    }

    @PutMapping
    public Long updateAccommodation(@Valid @RequestBody PriceDtoUpdate priceDtoUpdate){
        return priceAdapter.update(priceDtoUpdate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        priceAdapter.deleteById(id);
    }

}
