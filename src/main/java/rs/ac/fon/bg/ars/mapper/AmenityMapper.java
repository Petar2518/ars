package rs.ac.fon.bg.ars.mapper;


import org.mapstruct.Mapper;
import rs.ac.fon.bg.ars.domain.AmenityDomain;
import rs.ac.fon.bg.ars.dto.AmenityDto;
import rs.ac.fon.bg.ars.model.Amenity;

@Mapper(componentModel = "spring")
public interface AmenityMapper {

    AmenityDto domainToDto(AmenityDomain amenityDomain);

    AmenityDomain dtoToDomain(AmenityDto amenityDto);

    AmenityDomain entityToDomain(Amenity amenity);

    Amenity domainToEntity(AmenityDomain amenityDomain);
}
