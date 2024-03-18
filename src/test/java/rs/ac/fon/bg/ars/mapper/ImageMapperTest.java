package rs.ac.fon.bg.ars.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.AmenityDomain;
import rs.ac.fon.bg.ars.domain.ImageDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.dto.AccommodationDto;
import rs.ac.fon.bg.ars.dto.AmenityDto;
import rs.ac.fon.bg.ars.dto.ImageDto;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.Amenity;
import rs.ac.fon.bg.ars.model.Image;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class ImageMapperTest {

    ImageMapper mapper = Mappers.getMapper(ImageMapper.class);

    AccommodationMapper accommodationMapper = Mappers.getMapper(AccommodationMapper.class);

    @Test
    void fromEntityToDomain(){

        Accommodation accommodation = Accommodation.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        Image image = Image.builder()
                .image("Testing. . .".getBytes())
                .accommodation(accommodation)
                .build();

        ImageDomain imageDomain = mapper.entityToDomain(image);

        Assertions.assertNotNull(imageDomain);
        assertThat(imageDomain).extracting(ImageDomain::getImage).isEqualTo(image.getImage());
        assertEquals(imageDomain.getId(),image.getId());
    }

    @Test
    void fromDomainToEntity(){
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        ImageDomain imageDomain = ImageDomain.builder()
                .image("Testing. . .".getBytes())
                .accommodation(accommodationDomain)
                .build();

        Image image = mapper.domainToEntity(imageDomain);

        Assertions.assertNotNull(imageDomain);
        assertThat(imageDomain).extracting(ImageDomain::getImage).isEqualTo(image.getImage());
        assertEquals(imageDomain.getId(),image.getId());
    }

    @Test
    void fromDomainToDto(){
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        ImageDomain imageDomain = ImageDomain.builder()
                .image("Testing. . .".getBytes())
                .accommodation(accommodationDomain)
                .build();

        ImageDto imageDto = mapper.domainToDto(imageDomain);

        Assertions.assertNotNull(imageDomain);
        assertThat(imageDomain).extracting(ImageDomain::getImage).isEqualTo(imageDto.getImage());
        assertEquals(imageDomain.getId(),imageDto.getId());
    }
    @Test
    void fromDtoToDomain(){
        AccommodationDto accommodationDto = AccommodationDto.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        ImageDto imageDto = ImageDto.builder()
                .image("Testing. . .".getBytes())
                .accommodation(accommodationDto)
                .build();

        ImageDomain imageDomain = mapper.dtoToDomain(imageDto);

        Assertions.assertNotNull(imageDomain);
        assertThat(imageDomain).extracting(ImageDomain::getImage).isEqualTo(imageDto.getImage());
        assertEquals(imageDomain.getId(),imageDto.getId());
    }


}
