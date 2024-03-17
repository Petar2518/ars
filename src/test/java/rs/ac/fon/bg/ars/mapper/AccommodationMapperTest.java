package rs.ac.fon.bg.ars.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.dto.AccommodationDto;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class AccommodationMapperTest {

    AccommodationMapper mapper = Mappers.getMapper(AccommodationMapper.class);

    @Test
    void fromEntityToDomain(){
        Accommodation accommodation = Accommodation.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        AccommodationDomain accommodationDomain = mapper.entityToDomain(accommodation);

        Assertions.assertNotNull(accommodationDomain);
        assertEquals(accommodation.getAccommodationType(),accommodationDomain.getAccommodationType());
        assertEquals(accommodation.getName(),accommodationDomain.getName());
        assertEquals(accommodation.getDescription(),accommodationDomain.getDescription());
        assertEquals(accommodation.getHostId(),accommodationDomain.getHostId());
        assertEquals(accommodation.getId(),accommodationDomain.getId());
    }

    @Test
    void fromDomainToEntity(){
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        Accommodation accommodation = mapper.domainToEntity(accommodationDomain);

        assertNotNull(accommodation);
        assertEquals(accommodation.getAccommodationType(),accommodationDomain.getAccommodationType());
        assertEquals(accommodation.getName(),accommodationDomain.getName());
        assertEquals(accommodation.getDescription(),accommodationDomain.getDescription());
        assertEquals(accommodation.getHostId(),accommodationDomain.getHostId());
        assertEquals(accommodation.getId(),accommodationDomain.getId());
    }

    @Test
    void fromDomainToDto(){
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        AccommodationDto accommodationDto = mapper.domainToDto(accommodationDomain);

        assertNotNull(accommodationDto);
        assertEquals(accommodationDto.getAccommodationType(),accommodationDomain.getAccommodationType());
        assertEquals(accommodationDto.getName(),accommodationDomain.getName());
        assertEquals(accommodationDto.getDescription(),accommodationDomain.getDescription());
        assertEquals(accommodationDto.getHostId(),accommodationDomain.getHostId());
        assertEquals(accommodationDto.getId(),accommodationDomain.getId());
    }
    @Test
    void fromDtoToDomain(){
        AccommodationDto accommodationDto = AccommodationDto.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        AccommodationDomain accommodationDomain = mapper.dtoToDomain(accommodationDto);

        assertNotNull(accommodationDomain);
        assertEquals(accommodationDto.getAccommodationType(),accommodationDomain.getAccommodationType());
        assertEquals(accommodationDto.getName(),accommodationDomain.getName());
        assertEquals(accommodationDto.getDescription(),accommodationDomain.getDescription());
        assertEquals(accommodationDto.getHostId(),accommodationDomain.getHostId());
        assertEquals(accommodationDto.getId(),accommodationDomain.getId());
    }

    @Test
    void fromDtoUpdatedToDomainUpdated(){
        AccommodationDtoUpdate accommodationDto = AccommodationDtoUpdate.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .description("Hotel close to city center")
                .build();

        AccommodationDomainUpdate accommodationDomain = mapper.dtoUpdateToDomainUpdate(accommodationDto);

        assertNotNull(accommodationDomain);
        assertEquals(accommodationDto.getAccommodationType(),accommodationDomain.getAccommodationType());
        assertEquals(accommodationDto.getName(),accommodationDomain.getName());
        assertEquals(accommodationDto.getDescription(),accommodationDomain.getDescription());
        assertEquals(accommodationDto.getId(),accommodationDomain.getId());
    }

}
