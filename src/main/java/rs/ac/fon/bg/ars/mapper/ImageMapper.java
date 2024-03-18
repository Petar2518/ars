package rs.ac.fon.bg.ars.mapper;

import org.mapstruct.Mapper;
import rs.ac.fon.bg.ars.domain.AmenityDomain;
import rs.ac.fon.bg.ars.domain.ImageDomain;
import rs.ac.fon.bg.ars.dto.AmenityDto;
import rs.ac.fon.bg.ars.dto.ImageDto;
import rs.ac.fon.bg.ars.model.Amenity;
import rs.ac.fon.bg.ars.model.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    ImageDto domainToDto(ImageDomain imageDomain);

    ImageDomain dtoToDomain(ImageDto imageDto);

    ImageDomain entityToDomain(Image image);

    Image domainToEntity(ImageDomain imageDomain);
}
