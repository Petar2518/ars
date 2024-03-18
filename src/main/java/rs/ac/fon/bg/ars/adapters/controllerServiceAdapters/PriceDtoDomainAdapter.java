package rs.ac.fon.bg.ars.adapters.controllerServiceAdapters;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rs.ac.fon.bg.ars.dto.AmenityDto;
import rs.ac.fon.bg.ars.dto.PriceDto;
import rs.ac.fon.bg.ars.dto.dateFilter.PricesDate;
import rs.ac.fon.bg.ars.dto.update.PriceDtoUpdate;
import rs.ac.fon.bg.ars.mapper.AmenityMapper;
import rs.ac.fon.bg.ars.mapper.PriceMapper;
import rs.ac.fon.bg.ars.service.AmenityService;
import rs.ac.fon.bg.ars.service.PriceService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PriceDtoDomainAdapter {

    private final PriceService priceService;
    private final PriceMapper priceMapper;

    public Long save(PriceDto priceDto){
        return priceService.save(priceMapper.dtoToDomain(priceDto));
    }

    public List<PriceDto> getAll(Long accommodationId, PricesDate pricesDate,Pageable pageable){
        return priceService.getAll(accommodationId, pricesDate, pageable)
                .stream()
                .map(priceMapper::domainToDto)
                .toList();
    }
    public PriceDto findById(Long id){
        return priceMapper.domainToDto(priceService.findById(id));
    }

    public Long update(PriceDtoUpdate priceDtoUpdate){
        return priceService.update(priceMapper.dtoUpdateToDomainUpdate(priceDtoUpdate));
    }

    public void deleteById(Long id){
        priceService.deleteById(id);
    }
}
