package rs.ac.fon.bg.ars.mapper;

import org.mapstruct.Mapper;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.PriceDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.domain.update.PriceDomainUpdate;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDto;
import rs.ac.fon.bg.ars.dto.PriceDto;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;
import rs.ac.fon.bg.ars.dto.update.PriceDtoUpdate;
import rs.ac.fon.bg.ars.model.AccommodationUnit;
import rs.ac.fon.bg.ars.model.Price;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceDto domainToDto(PriceDomain priceDomain);

    PriceDomainUpdate dtoUpdateToDomainUpdate(PriceDtoUpdate priceDtoUpdate);

    PriceDomain dtoToDomain(PriceDto priceDto);

    PriceDomain entityToDomain(Price price);

    Price domainToEntity(PriceDomain priceDomain);
}