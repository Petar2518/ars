package rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.PriceDomain;
import rs.ac.fon.bg.ars.mapper.AccommodationUnitMapper;
import rs.ac.fon.bg.ars.mapper.PriceMapper;
import rs.ac.fon.bg.ars.repository.AccommodationUnitRepository;
import rs.ac.fon.bg.ars.repository.PriceRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PriceDomainEntityAdapter {

    private final AccommodationUnitMapper accommodationUnitMapper;
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    public Long save(PriceDomain priceDomain){
        return priceRepository.save(priceMapper.domainToEntity(priceDomain)).getId();
    }

    public Optional<PriceDomain> findById(Long id){
        return priceRepository.findById(id)
                .map(priceMapper::entityToDomain);
    }

    public void deleteById(Long id){
        priceRepository.deleteById(id);
    }

    public List<PriceDomain> findAll(Long accommodationId, LocalDate startDate, LocalDate endDate, Pageable pageable){
        return priceRepository.findAllByAccommodationUnitId(accommodationId,startDate,endDate,pageable)
                .getContent()
                .stream()
                .map(priceMapper::entityToDomain)
                .toList();
    }

    public List<PriceDomain> findUnavailableDates(AccommodationUnitDomain accommodationUnit, LocalDate before, LocalDate after){
        return priceRepository.findAllAccommodationUnitsInDates(accommodationUnitMapper.domainToEntity(accommodationUnit),before,after)
                .stream().map(priceMapper::entityToDomain)
                .toList();
    }

}
