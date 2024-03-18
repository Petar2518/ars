package rs.ac.fon.bg.ars.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.AmenityDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.dto.AccommodationDto;
import rs.ac.fon.bg.ars.dto.AmenityDto;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.Amenity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class AmenitiesMapperTest {

    AmenityMapper mapper = Mappers.getMapper(AmenityMapper.class);

    @Test
    void fromEntityToDomain(){
        Amenity amenity = Amenity.builder()
                .amenity("Pool")
                .build();

        AmenityDomain amenityDomain = mapper.entityToDomain(amenity);

        Assertions.assertNotNull(amenityDomain);
        assertEquals(amenityDomain.getAmenity(),amenity.getAmenity());
        assertEquals(amenityDomain.getId(),amenity.getId());
    }

    @Test
    void fromDomainToEntity(){
        AmenityDomain amenityDomain = AmenityDomain.builder()
                .amenity("Pool")
                .build();

        Amenity amenity = mapper.domainToEntity(amenityDomain);

        Assertions.assertNotNull(amenityDomain);
        assertEquals(amenityDomain.getAmenity(),amenity.getAmenity());
        assertEquals(amenityDomain.getId(),amenity.getId());
    }

    @Test
    void fromDomainToDto(){
        AmenityDomain amenityDomain = AmenityDomain.builder()
                .amenity("Pool")
                .build();

        AmenityDto amenityDto = mapper.domainToDto(amenityDomain);

        Assertions.assertNotNull(amenityDomain);
        assertEquals(amenityDomain.getAmenity(),amenityDto.getAmenity());
        assertEquals(amenityDomain.getId(),amenityDto.getId());
    }
    @Test
    void fromDtoToDomain(){
        AmenityDto amenityDto = AmenityDto.builder()
                .amenity("Pool")
                .build();

        AmenityDomain amenityDomain = mapper.dtoToDomain(amenityDto);

        Assertions.assertNotNull(amenityDomain);
        assertEquals(amenityDomain.getAmenity(),amenityDto.getAmenity());
        assertEquals(amenityDomain.getId(),amenityDto.getId());
    }


}
