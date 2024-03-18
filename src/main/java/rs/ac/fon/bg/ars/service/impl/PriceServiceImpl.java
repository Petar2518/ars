package rs.ac.fon.bg.ars.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters.PriceDomainEntityAdapter;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.PriceDomain;
import rs.ac.fon.bg.ars.domain.update.PriceDomainUpdate;
import rs.ac.fon.bg.ars.dto.dateFilter.PricesDate;
import rs.ac.fon.bg.ars.exception.specific.DateUnavailableException;
import rs.ac.fon.bg.ars.exception.specific.MapperException;
import rs.ac.fon.bg.ars.exception.specific.PriceNotFoundException;
import rs.ac.fon.bg.ars.service.PriceService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceDomainEntityAdapter serviceRepositoryAdapter;
    private final ObjectMapper objectMapper;
    @Override
    public Long save(PriceDomain priceDomain) {
        AccommodationUnitDomain accommodationUnitDomain = priceDomain.getAccommodationUnit();
        LocalDate dateFrom = priceDomain.getDateFrom();
        LocalDate dateTo = priceDomain.getDateTo();
        if(!serviceRepositoryAdapter.findUnavailableDates(accommodationUnitDomain,dateFrom,dateTo).isEmpty()){
            throw new DateUnavailableException();
        }
        return serviceRepositoryAdapter.save(priceDomain);
    }

    @Override
    public List<PriceDomain> getAll(Long accommodationId, PricesDate pricesDate, Pageable pageable) {
        LocalDate startDate;
        LocalDate endDate;

        if(pricesDate.getStartDate()==null){
            startDate = LocalDate.EPOCH;
        }else {
            startDate = pricesDate.getStartDate();
        }
        if(pricesDate.getEndDate()==null){
            endDate = LocalDate.of(2100,1,1);
        }else {
            endDate = pricesDate.getEndDate();
        }
        return serviceRepositoryAdapter.findAll(accommodationId, startDate, endDate, pageable);
    }

    @Override
    public void deleteById(Long id) {
        serviceRepositoryAdapter.deleteById(id);
    }

    @Override
    public Long update(PriceDomainUpdate priceDomainUpdate) {
        PriceDomain existingPriceById = findById(priceDomainUpdate.getId());
        try {
            objectMapper.updateValue(existingPriceById,priceDomainUpdate);
        }catch (JsonMappingException e){
            throw new MapperException();
        }
        return this.save(existingPriceById);
    }

    @Override
    public PriceDomain findById(Long id) {
        return serviceRepositoryAdapter.findById(id).orElseThrow(()-> new PriceNotFoundException(id));
    }
}
