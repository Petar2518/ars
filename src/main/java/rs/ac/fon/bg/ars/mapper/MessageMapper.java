package rs.ac.fon.bg.ars.mapper;

import org.mapstruct.Mapper;
import rs.ac.fon.bg.ars.dto.message.*;
import rs.ac.fon.bg.ars.model.*;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    AccommodationMessageDto accommodationEntityToMessageDto(Accommodation accommodation);

    Accommodation accommodationMessageDtoToEntity(AccommodationMessageDto accommodationMessageDto);

    AccommodationUnitMessageDto accommodationUnitEntityToMessageDto(AccommodationUnit accommodationUnit);

    AccommodationUnit accommodationUnitMessageDtoToEntity(AccommodationUnitMessageDto accommodationUnitMessageDto);

    AddressMessageDto addressEntityToMessageDto(Address address);

    Address addressMessageDtoToEntity(AddressMessageDto addressMessageDto);

    AmenityMessageDto amenityEntityToMessageDto(Amenity amenity);

    Amenity amenityMessageDtoToEntity(AmenityMessageDto amenityMessageDto);

    PriceMessageDto priceEntityToMessageDto(Price price);

    Price priceMessageDtoToEntity(PriceMessageDto priceMessageDto);
}
