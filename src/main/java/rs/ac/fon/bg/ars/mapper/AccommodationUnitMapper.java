package rs.ac.fon.bg.ars.mapper;

import org.mapstruct.Mapper;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.domain.update.AccommodationUnitDomainUpdate;
import rs.ac.fon.bg.ars.dto.AccommodationDto;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDto;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDtoUpdate;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationUnit;

@Mapper(componentModel = "spring")
public interface AccommodationUnitMapper {

    AccommodationUnitDto domainToDto(AccommodationUnitDomain accommodationUnitDomain);

    AccommodationUnitDomainUpdate dtoUpdateToDomainUpdate(AccommodationUnitDtoUpdate accommodationDtoUpdate);

    AccommodationUnitDomain dtoToDomain(AccommodationUnitDto accommodationUnitDto);

    AccommodationUnitDomain entityToDomain(AccommodationUnit accommodationUnit);

    AccommodationUnit domainToEntity(AccommodationUnitDomain accommodationUnitDomain);
}