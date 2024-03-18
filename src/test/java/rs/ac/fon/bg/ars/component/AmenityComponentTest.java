package rs.ac.fon.bg.ars.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import rs.ac.fon.bg.ars.dto.AccommodationDto;
import rs.ac.fon.bg.ars.dto.AmenityDto;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;
import rs.ac.fon.bg.ars.mapper.AccommodationMapper;
import rs.ac.fon.bg.ars.mapper.AmenityMapper;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.util.ComponentTestBase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmenityComponentTest extends ComponentTestBase {

    @Autowired
    AmenityMapper mapper;

    @Autowired
    WebTestClient webTestClient;

    private final String AMENITY_URI = "/amenities";

    String name = "Hilton Belgrade";
    String updatedName = "Hotel";

    private AmenityDto createAmenity(){
        return AmenityDto.builder()
                .amenity("Pool")
                .build();
    }

    private Long postAmenity(AmenityDto amenityDto){
        return webTestClient.post()
                .uri(AMENITY_URI)
                .bodyValue(amenityDto)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Long.class)
                .getResponseBody().toStream().findAny().get();
    }

    private AmenityDto getAmenity(Long id){
        return webTestClient.get()
                .uri(AMENITY_URI+"/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AmenityDto.class)
                .returnResult().getResponseBody();
    }


    @Test
    void addAmenity(){
        AmenityDto amenityDto = createAmenity();

        Long amenityId = postAmenity(amenityDto);

        amenityDto.setId(amenityId);

        List<AmenityDto> result
                = webTestClient.get()
                .uri(AMENITY_URI + "?page=0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<AmenityDto>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);
        Long idFromDatabase = result
                .stream()
                .filter(value-> value.getAmenity().equals(amenityDto.getAmenity()))
                .map(AmenityDto::getId)
                .findAny()
                .orElseThrow();

        assertEquals(idFromDatabase,amenityId);
        amenityDto.setId(idFromDatabase);
        AmenityDto amenityDtoResult = getAmenity(idFromDatabase);

        assertEquals(amenityDtoResult.getAmenity(), amenityDto.getAmenity());
    }

    @Test
    void addAmenityValuesNull(){
       AmenityDto amenityDto = AmenityDto.builder()
               .amenity(null)
               .build();

        webTestClient.post()
                .uri(AMENITY_URI)
                .bodyValue(amenityDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateAmenity(){
        AmenityDto amenityDto = createAmenity();

        Long amenityId = postAmenity(amenityDto);

        amenityDto.setId(amenityId);

        List<AmenityDto> result
                = webTestClient.get()
                .uri(AMENITY_URI + "?page=0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<AmenityDto>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);

        AmenityDto amenityDtoUpdated = AmenityDto.builder()
                .id(amenityId)
                .amenity("Parking")
                .build();

        webTestClient.put()
                .uri(AMENITY_URI)
                .bodyValue(amenityDtoUpdated)
                .exchange()
                .expectStatus().isOk();

        AmenityDto amenityDtoResult = getAmenity(amenityId);

        assertEquals(amenityDtoResult.getAmenity(),amenityDtoUpdated.getAmenity());

    }

    @Test
    void deleteAmenity(){

        AmenityDto amenityDto = createAmenity();

        Long amenityId = postAmenity(amenityDto);

        amenityDto.setId(amenityId);

        List<AccommodationDto> result
                = webTestClient.get()
                .uri(AMENITY_URI + "?page=0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<AccommodationDto>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);

        webTestClient.delete()
                .uri(AMENITY_URI + "/{id}", amenityId)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    void updateAmenityThatDoesNotExist(){
        AmenityDto amenityDto = AmenityDto.builder()
                .id(0L)
                .amenity("Parking")
                .build();

        webTestClient.put()
                .uri(AMENITY_URI)
                .bodyValue(amenityDto)
                .exchange()
                .expectStatus().isNotFound();
    }
}
