package rs.ac.fon.bg.ars.mapper;

import org.mapstruct.Mapper;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.dto.AccommodationDto;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;
import rs.ac.fon.bg.ars.model.Accommodation;

@Mapper(componentModel = "spring")
public interface AccommodationMapper {

    AccommodationDto domainToDto(AccommodationDomain accommodationDomain);

    AccommodationDomainUpdate dtoUpdateToDomainUpdate(AccommodationDtoUpdate accommodationUpdate);

    AccommodationDomain dtoToDomain(AccommodationDto accommodationDto);

    AccommodationDomain entityToDomain(Accommodation accommodation);

    Accommodation domainToEntity(AccommodationDomain accommodationDomain);
}
