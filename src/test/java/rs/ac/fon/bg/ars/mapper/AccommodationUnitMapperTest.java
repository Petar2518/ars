package rs.ac.fon.bg.ars.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.domain.update.AccommodationUnitDomainUpdate;
import rs.ac.fon.bg.ars.dto.AccommodationDto;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDto;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDtoUpdate;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.AccommodationUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class AccommodationUnitMapperTest {

    AccommodationUnitMapper mapper = Mappers.getMapper(AccommodationUnitMapper.class);

    AccommodationMapper accommodationMapper = Mappers.getMapper(AccommodationMapper.class);

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
        AccommodationUnitDomain accommodationUnitDomain = mapper.entityToDomain(accommodationUnit);

        Assertions.assertNotNull(accommodationUnitDomain);
        assertEquals(accommodationUnit.getName(),accommodationUnitDomain.getName());
        assertEquals(accommodationUnitDomain.getDescription(),accommodationUnit.getDescription());
        assertEquals(accommodationUnitDomain.getCapacity(),accommodationUnit.getCapacity());
        assertEquals(accommodationMapper.entityToDomain(accommodationUnit.getAccommodation()).getId(),accommodationUnitDomain.getAccommodation().getId());
        assertEquals(accommodationUnitDomain.getId(),accommodationUnit.getId());
    }

    @Test
    void fromDomainToEntity(){
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        AccommodationUnitDomain accommodationUnitDomain = AccommodationUnitDomain.builder()
                .name("Double room")
                .capacity(2)
                .description("Large room")
                .accommodation(accommodationDomain)
                .build();
        AccommodationUnit accommodationUnit = mapper.domainToEntity(accommodationUnitDomain);

        Assertions.assertNotNull(accommodationUnit);
        assertEquals(accommodationUnit.getName(),accommodationUnitDomain.getName());
        assertEquals(accommodationUnitDomain.getDescription(),accommodationUnit.getDescription());
        assertEquals(accommodationUnitDomain.getCapacity(),accommodationUnit.getCapacity());
        assertEquals(accommodationMapper.entityToDomain(accommodationUnit.getAccommodation()).getId(),accommodationUnitDomain.getAccommodation().getId());
        assertEquals(accommodationUnitDomain.getId(),accommodationUnit.getId());
    }

    @Test
    void fromDomainToDto(){
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        AccommodationUnitDomain accommodationUnitDomain = AccommodationUnitDomain.builder()
                .name("Double room")
                .capacity(2)
                .description("Large room")
                .accommodation(accommodationDomain)
                .build();
        AccommodationUnitDto accommodationUnitDto = mapper.domainToDto(accommodationUnitDomain);

        Assertions.assertNotNull(accommodationUnitDto);
        assertEquals(accommodationUnitDto.getName(),accommodationUnitDomain.getName());
        assertEquals(accommodationUnitDomain.getDescription(),accommodationUnitDto.getDescription());
        assertEquals(accommodationUnitDomain.getCapacity(),accommodationUnitDto.getCapacity());
        assertEquals(accommodationMapper.dtoToDomain(accommodationUnitDto.getAccommodation()).getId(),accommodationUnitDomain.getAccommodation().getId());
        assertEquals(accommodationUnitDomain.getId(),accommodationUnitDto.getId());
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
        AccommodationUnitDomain accommodationUnitDomain = mapper.dtoToDomain(accommodationUnitDto);

        Assertions.assertNotNull(accommodationUnitDomain);
        assertEquals(accommodationUnitDto.getName(),accommodationUnitDomain.getName());
        assertEquals(accommodationUnitDomain.getDescription(),accommodationUnitDto.getDescription());
        assertEquals(accommodationUnitDomain.getCapacity(),accommodationUnitDto.getCapacity());
        assertEquals(accommodationMapper.dtoToDomain(accommodationUnitDto.getAccommodation()).getId(),accommodationUnitDomain.getAccommodation().getId());
        assertEquals(accommodationUnitDomain.getId(),accommodationUnitDto.getId());
    }

    @Test
    void fromDtoUpdatedToDomainUpdated(){

        AccommodationUnitDtoUpdate accommodationUnitDto= AccommodationUnitDtoUpdate.builder()
                .name("Double room")
                .capacity(2)
                .description("Large room")
                .build();
        AccommodationUnitDomainUpdate accommodationUnitDomain = mapper.dtoUpdateToDomainUpdate(accommodationUnitDto);

        Assertions.assertNotNull(accommodationUnitDomain);
        assertEquals(accommodationUnitDto.getName(),accommodationUnitDomain.getName());
        assertEquals(accommodationUnitDomain.getDescription(),accommodationUnitDto.getDescription());
        assertEquals(accommodationUnitDomain.getCapacity(),accommodationUnitDto.getCapacity());
        assertEquals(accommodationUnitDomain.getId(),accommodationUnitDto.getId());
    }

}
