package rs.ac.fon.bg.ars.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.PriceDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.domain.update.AccommodationUnitDomainUpdate;
import rs.ac.fon.bg.ars.domain.update.PriceDomainUpdate;
import rs.ac.fon.bg.ars.dto.AccommodationDto;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDto;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDtoUpdate;
import rs.ac.fon.bg.ars.dto.PriceDto;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;
import rs.ac.fon.bg.ars.dto.update.PriceDtoUpdate;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.AccommodationUnit;
import rs.ac.fon.bg.ars.model.Price;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class PriceMapperTest {

    AccommodationUnitMapper accommodationUnitMapper = Mappers.getMapper(AccommodationUnitMapper.class);

    PriceMapper mapper = Mappers.getMapper(PriceMapper.class);

    @Test
    void fromEntityToDomain(){
        Accommodation accommodation = Accommodation.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        AccommodationUnit accommodationUnit = AccommodationUnit.builder()
                .name("Double room")
                .capacity(2)
                .description("Large room")
                .accommodation(accommodation)
                .build();

        Price price = Price.builder()
                .id(1L)
                .dateFrom(LocalDate.of(2025,3,24))
                .dateTo(LocalDate.of(2025,3,26))
                .amount(BigDecimal.valueOf(110.00))
                .accommodationUnit(accommodationUnit)
                .build();

        PriceDomain priceDomain = mapper.entityToDomain(price);

        assertNotNull(priceDomain);
        assertEquals(priceDomain.getDateFrom(),price.getDateFrom());
        assertEquals(priceDomain.getDateTo(),price.getDateTo());
        assertEquals(priceDomain.getAmount(),price.getAmount());
        assertEquals(priceDomain.getAccommodationUnit().getAccommodation().getId(),accommodationUnitMapper.entityToDomain(price.getAccommodationUnit()).getId());
    }

    @Test
    void fromDomainToEntity(){
        AccommodationDomain accommodation = AccommodationDomain.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        AccommodationUnitDomain accommodationUnit = AccommodationUnitDomain.builder()
                .name("Double room")
                .capacity(2)
                .description("Large room")
                .accommodation(accommodation)
                .build();

        PriceDomain priceDomain = PriceDomain.builder()
                .id(1L)
                .dateFrom(LocalDate.of(2025,3,24))
                .dateTo(LocalDate.of(2025,3,26))
                .amount(BigDecimal.valueOf(110.00))
                .accommodationUnit(accommodationUnit)
                .build();

        Price price = mapper.domainToEntity(priceDomain);

        assertNotNull(priceDomain);
        assertEquals(priceDomain.getDateFrom(),price.getDateFrom());
        assertEquals(priceDomain.getDateTo(),price.getDateTo());
        assertEquals(priceDomain.getAmount(),price.getAmount());
        assertEquals(priceDomain.getAccommodationUnit().getAccommodation().getId(),accommodationUnitMapper.entityToDomain(price.getAccommodationUnit()).getId());
    }

    @Test
    void fromDomainToDto(){
        AccommodationDomain accommodation = AccommodationDomain.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        AccommodationUnitDomain accommodationUnit = AccommodationUnitDomain.builder()
                .name("Double room")
                .capacity(2)
                .description("Large room")
                .accommodation(accommodation)
                .build();

        PriceDomain priceDomain = PriceDomain.builder()
                .id(1L)
                .dateFrom(LocalDate.of(2025,3,24))
                .dateTo(LocalDate.of(2025,3,26))
                .amount(BigDecimal.valueOf(110.00))
                .accommodationUnit(accommodationUnit)
                .build();

        PriceDto priceDto = mapper.domainToDto(priceDomain);

        assertNotNull(priceDomain);
        assertEquals(priceDomain.getDateFrom(),priceDto.getDateFrom());
        assertEquals(priceDomain.getDateTo(),priceDto.getDateTo());
        assertEquals(priceDomain.getAmount(),priceDto.getAmount());
        assertEquals(priceDomain.getAccommodationUnit().getAccommodation().getId(),accommodationUnitMapper.dtoToDomain(priceDto.getAccommodationUnit()).getId());
    }
    @Test
    void fromDtoToDomain(){
        AccommodationDto accommodationDto = AccommodationDto.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        AccommodationUnitDto accommodationUnitDto= AccommodationUnitDto.builder()
                .name("Double room")
                .capacity(2)
                .description("Large room")
                .accommodation(accommodationDto)
                .build();

        PriceDto priceDto = PriceDto.builder()
                .id(1L)
                .dateFrom(LocalDate.of(2025,3,24))
                .dateTo(LocalDate.of(2025,3,26))
                .amount(BigDecimal.valueOf(110.00))
                .accommodationUnit(accommodationUnitDto)
                .build();

        PriceDomain priceDomain = mapper.dtoToDomain(priceDto);

        assertNotNull(priceDomain);
        assertEquals(priceDomain.getDateFrom(),priceDto.getDateFrom());
        assertEquals(priceDomain.getDateTo(),priceDto.getDateTo());
        assertEquals(priceDomain.getAmount(),priceDto.getAmount());
        assertEquals(priceDomain.getAccommodationUnit().getAccommodation().getId(),accommodationUnitMapper.dtoToDomain(priceDto.getAccommodationUnit()).getId());
    }

    @Test
    void fromDtoUpdatedToDomainUpdated(){

        PriceDtoUpdate priceDto = PriceDtoUpdate.builder()
                .id(1L)
                .dateFrom(LocalDate.of(2025,3,24))
                .dateTo(LocalDate.of(2025,3,26))
                .amount(BigDecimal.valueOf(110.00))
                .build();

        PriceDomainUpdate priceDomain = mapper.dtoUpdateToDomainUpdate(priceDto);

        assertNotNull(priceDomain);
        assertEquals(priceDomain.getDateFrom(),priceDto.getDateFrom());
        assertEquals(priceDomain.getDateTo(),priceDto.getDateTo());
        assertEquals(priceDomain.getAmount(),priceDto.getAmount());
    }

}
