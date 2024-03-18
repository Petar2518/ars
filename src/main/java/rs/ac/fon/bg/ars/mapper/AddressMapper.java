package rs.ac.fon.bg.ars.mapper;

import org.mapstruct.Mapper;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.AddressDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.domain.update.AddressDomainUpdate;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDto;
import rs.ac.fon.bg.ars.dto.AddressDto;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;
import rs.ac.fon.bg.ars.dto.update.AddressDtoUpdate;
import rs.ac.fon.bg.ars.model.AccommodationUnit;
import rs.ac.fon.bg.ars.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto domainToDto(AddressDomain addressDomain);

    AddressDomainUpdate dtoUpdateToDomainUpdate(AddressDtoUpdate addressDtoUpdate);

    AddressDomain dtoToDomain(AddressDto addressDto);

    AddressDomain entityToDomain(Address address);

    Address domainToEntity(AddressDomain addressDomain);
}
