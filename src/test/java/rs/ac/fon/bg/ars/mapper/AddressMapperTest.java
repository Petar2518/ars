package rs.ac.fon.bg.ars.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.AddressDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationUnitDomainUpdate;
import rs.ac.fon.bg.ars.domain.update.AddressDomainUpdate;
import rs.ac.fon.bg.ars.dto.AccommodationDto;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDto;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDtoUpdate;
import rs.ac.fon.bg.ars.dto.AddressDto;
import rs.ac.fon.bg.ars.dto.update.AddressDtoUpdate;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.AccommodationUnit;
import rs.ac.fon.bg.ars.model.Address;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)

public class AddressMapperTest {
    AddressMapper mapper = Mappers.getMapper(AddressMapper.class);

    AccommodationMapper accommodationMapper = Mappers.getMapper(AccommodationMapper.class);

    @Test
    void fromEntityToDomain(){
        Accommodation accommodation = Accommodation.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        Address address = Address.builder()
                .accommodation(accommodation)
                .country("Serbia")
                .city("Belgrade")
                .postalCode("11000")
                .street("Jove Ilica")
                .streetNumber("154")
                .build();
        AddressDomain addressDomain = mapper.entityToDomain(address);

        Assertions.assertNotNull(addressDomain);
        assertEquals(address.getCountry(),addressDomain.getCountry());
        assertEquals(address.getCity(),addressDomain.getCity());
        assertEquals(address.getStreet(),addressDomain.getStreet());
        assertEquals(address.getStreetNumber(),addressDomain.getStreetNumber());
        assertEquals(address.getPostalCode(),addressDomain.getPostalCode());
        assertEquals(accommodationMapper.entityToDomain(address.getAccommodation()).getId(),addressDomain.getAccommodation().getId());
        assertEquals(address.getId(),addressDomain.getId());
    }

    @Test
    void fromDomainToEntity(){
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        AddressDomain addressDomain = AddressDomain.builder()
                .accommodation(accommodationDomain)
                .country("Serbia")
                .city("Belgrade")
                .postalCode("11000")
                .street("Jove Ilica")
                .streetNumber("154")
                .build();
        Address address = mapper.domainToEntity(addressDomain);

        Assertions.assertNotNull(addressDomain);
        assertEquals(address.getCountry(),addressDomain.getCountry());
        assertEquals(address.getCity(),addressDomain.getCity());
        assertEquals(address.getStreet(),addressDomain.getStreet());
        assertEquals(address.getStreetNumber(),addressDomain.getStreetNumber());
        assertEquals(address.getPostalCode(),addressDomain.getPostalCode());
        assertEquals(accommodationMapper.entityToDomain(address.getAccommodation()).getId(),addressDomain.getAccommodation().getId());
        assertEquals(address.getId(),addressDomain.getId());
    }

    @Test
    void fromDomainToDto(){
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        AddressDomain address = AddressDomain.builder()
                .accommodation(accommodationDomain)
                .country("Serbia")
                .city("Belgrade")
                .postalCode("11000")
                .street("Jove Ilica")
                .streetNumber("154")
                .build();
        AddressDto addressDto = mapper.domainToDto(address);

        Assertions.assertNotNull(addressDto);
        assertEquals(address.getCountry(),addressDto.getCountry());
        assertEquals(address.getCity(),addressDto.getCity());
        assertEquals(address.getStreet(),addressDto.getStreet());
        assertEquals(address.getStreetNumber(),addressDto.getStreetNumber());
        assertEquals(address.getPostalCode(),addressDto.getPostalCode());
        assertEquals(accommodationMapper.domainToDto(address.getAccommodation()).getId(),addressDto.getAccommodation().getId());
        assertEquals(address.getId(),addressDto.getId());
    }
    @Test
    void fromDtoToDomain(){
        AccommodationDto accommodationDto = AccommodationDto.builder()
                .name("Hyatt")
                .accommodationType(AccommodationType.HOTEL)
                .hostId(5L)
                .description("Hotel close to city center")
                .build();

        AddressDto address = AddressDto.builder()
                .accommodation(accommodationDto)
                .country("Serbia")
                .city("Belgrade")
                .postalCode("11000")
                .street("Jove Ilica")
                .streetNumber("154")
                .build();
        AddressDomain addressDomain = mapper.dtoToDomain(address);

        Assertions.assertNotNull(addressDomain);
        assertEquals(address.getCountry(),addressDomain.getCountry());
        assertEquals(address.getCity(),addressDomain.getCity());
        assertEquals(address.getStreet(),addressDomain.getStreet());
        assertEquals(address.getStreetNumber(),addressDomain.getStreetNumber());
        assertEquals(address.getPostalCode(),addressDomain.getPostalCode());
        assertEquals(accommodationMapper.dtoToDomain(address.getAccommodation()).getId(),addressDomain.getAccommodation().getId());
        assertEquals(address.getId(),addressDomain.getId());
    }

    @Test
    void fromDtoUpdatedToDomainUpdated(){

        AddressDtoUpdate addressDtoUpdate = AddressDtoUpdate.builder()
                .postalCode("11000")
                .street("Jove Ilica")
                .streetNumber("154")
                .build();

        AddressDomainUpdate addressDomainUpdate = mapper.dtoUpdateToDomainUpdate(addressDtoUpdate);
        Assertions.assertNotNull(addressDomainUpdate);
        assertEquals(addressDtoUpdate.getStreet(),addressDomainUpdate.getStreet());
        assertEquals(addressDtoUpdate.getStreetNumber(),addressDomainUpdate.getStreetNumber());
        assertEquals(addressDtoUpdate.getPostalCode(),addressDomainUpdate.getPostalCode());
        assertEquals(addressDtoUpdate.getId(),addressDomainUpdate.getId());
    }

}
