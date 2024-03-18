package rs.ac.fon.bg.ars.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import rs.ac.fon.bg.ars.dto.*;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDtoUpdate;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;
import rs.ac.fon.bg.ars.dto.update.AddressDtoUpdate;
import rs.ac.fon.bg.ars.mapper.AccommodationMapper;
import rs.ac.fon.bg.ars.mapper.AccommodationUnitMapper;
import rs.ac.fon.bg.ars.mapper.AddressMapper;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.AccommodationUnit;
import rs.ac.fon.bg.ars.util.ComponentTestBase;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressComponentTest extends ComponentTestBase {

    @Autowired
    AddressMapper mapper;

    @Autowired
    WebTestClient webTestClient;

    private final String ADDRESS_URI = "/addresses";

    String name = "Hyatt Belgrade";
    String streetUpdated= "Jovana Markovica";
    String streetNumberUpdated = "1";

    private AccommodationDto createAccommodation(){
        return AccommodationDto.builder()
                .name(name)
                .description("Hotel close to city center")
                .hostId(5L)
                .accommodationType(AccommodationType.HOTEL)
                .build();
    }

    private Long postAccommodation(AccommodationDto accommodationDto){
        return webTestClient.post()
                .uri("/accommodations")
                .bodyValue(accommodationDto)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Long.class)
                .getResponseBody().toStream().findAny().get();
    }

    private AddressDto createAddress(AccommodationDto accommodationDto){
        return AddressDto.builder()
                .id(accommodationDto.getId())
                .accommodation(accommodationDto)
                .country("Serbia")
                .city("Belgrade")
                .postalCode("11000")
                .street("Jove Ilica")
                .streetNumber("154")
                .build();
    }

    private Long postAddress(AddressDto addressDto){
        return webTestClient.post()
                .uri(ADDRESS_URI)
                .bodyValue(addressDto)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Long.class)
                .getResponseBody().toStream().findAny().get();
    }

    private AddressDto getAddress(Long id){
        return webTestClient.get()
                .uri(ADDRESS_URI+"/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AddressDto.class)
                .returnResult().getResponseBody();
    }


    @Test
    void addAddress() {
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AddressDto addressDto = createAddress(accommodationDto);

        Long addressId = postAddress(addressDto);

        addressDto.setId(addressId);

        AddressDto addressDtoResult = getAddress(addressId);

        List<AddressDto> result
                = webTestClient.get()
                .uri(ADDRESS_URI + "/" + addressId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<AddressDto>() {
                })
                .returnResult()
                .getResponseBody();


        assertThat(result).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").contains(addressDtoResult);
    }
    @Test
    void addAddressValuesNull(){
        AccommodationDto accommodationDto = createAccommodation();

        Long id = postAccommodation(accommodationDto);

        accommodationDto.setId(id);

        AddressDto addressDto = AddressDto.builder()
                .id(id)
                .accommodation(accommodationDto)
                .country("Serbia")
                .city("Belgrade")
                .postalCode("11000")
                .streetNumber("154")
                .build();

        webTestClient.post()
                .uri(ADDRESS_URI)
                .bodyValue(addressDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateAddress(){
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AddressDto addressDto = createAddress(accommodationDto);

        Long addressId = postAddress(addressDto);

        addressDto.setId(addressId);


        List<AddressDto> result
                = webTestClient.get()
                .uri(ADDRESS_URI+"/"+addressId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<AddressDto>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);

        AddressDtoUpdate addressDtoUpdate = AddressDtoUpdate.builder()
                .id(addressId)
                .postalCode("11000")
                .street(streetUpdated)
                .streetNumber(streetNumberUpdated)
                .build();

        webTestClient.put()
                .uri(ADDRESS_URI)
                .bodyValue(addressDtoUpdate)
                .exchange()
                .expectStatus().isOk();

        AddressDto addressResult = getAddress(addressId);

        assertEquals(addressResult.getStreet(),addressDtoUpdate.getStreet());
        assertEquals(addressResult.getStreetNumber(),addressDtoUpdate.getStreetNumber());

        assertEquals(accommodationId,addressId);

    }

    @Test
    void deleteAddress(){

        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AddressDto addressDto = createAddress(accommodationDto);

        Long addressId = postAddress(addressDto);

        addressDto.setId(addressId);

        List<AddressDto> result
                = webTestClient.get()
                .uri(ADDRESS_URI+"/" + addressId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<AddressDto>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);

        webTestClient.delete()
                .uri(ADDRESS_URI+"/{id}", addressId)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    void updateAddressThatDoesNotExist(){

        AccommodationDto accommodationDtoUpdate = createAccommodation();
        Long id = postAccommodation(accommodationDtoUpdate);

        accommodationDtoUpdate.setId(id);

        Long accommodationId = postAccommodation(accommodationDtoUpdate);

        accommodationDtoUpdate.setId(accommodationId);

        AddressDtoUpdate addressDto = AddressDtoUpdate.builder()
                .id(0L)
                .postalCode("11000")
                .street(streetUpdated)
                .streetNumber(streetNumberUpdated)
                .build();

        webTestClient.put()
                .uri(ADDRESS_URI)
                .bodyValue(addressDto)
                .exchange()
                .expectStatus().isNotFound();
    }
}
