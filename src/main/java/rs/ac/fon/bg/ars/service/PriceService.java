package rs.ac.fon.bg.ars.service;

import org.springframework.data.domain.Pageable;
import rs.ac.fon.bg.ars.domain.ImageDomain;
import rs.ac.fon.bg.ars.domain.PriceDomain;
import rs.ac.fon.bg.ars.domain.update.PriceDomainUpdate;
import rs.ac.fon.bg.ars.dto.dateFilter.PricesDate;

import java.util.List;

public interface PriceService {
    Long save(PriceDomain priceDomain);

    List<PriceDomain> getAll(Long accommodationId, PricesDate pricesDate, Pageable pageable);

    void deleteById(Long id);

    Long update(PriceDomainUpdate priceDomainUpdate);

    PriceDomain findById(Long id);
}
