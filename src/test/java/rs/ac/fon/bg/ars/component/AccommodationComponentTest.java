package rs.ac.fon.bg.ars.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import rs.ac.fon.bg.ars.dto.AccommodationDto;
import rs.ac.fon.bg.ars.dto.AmenityDto;
import rs.ac.fon.bg.ars.dto.message.MQTransferObject;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;
import rs.ac.fon.bg.ars.mapper.AccommodationMapper;
import rs.ac.fon.bg.ars.mapper.MessageMapper;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.util.ComponentTestBase;
import rs.ac.fon.bg.ars.util.RabbitListenerTestComponent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccommodationComponentTest extends ComponentTestBase {

    @Autowired
    AccommodationMapper mapper;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    RabbitListenerTestComponent rabbitListener;

    @Autowired
    WebTestClient webTestClient;

    private final String ACCOMMODATION_URI = "/accommodations";

    String name = "Hilton Belgrade";
    String updatedName = "Hotel";

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
                .uri(ACCOMMODATION_URI)
                .bodyValue(accommodationDto)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Long.class)
                .getResponseBody().toStream().findAny().get();
    }

    private AccommodationDto getAccommodation(Long id){
        return webTestClient.get()
                .uri(ACCOMMODATION_URI+"/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccommodationDto.class)
                .returnResult().getResponseBody();
    }


    @Test
    void addAccommodation(){
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        List<AccommodationDto> result
                = webTestClient.get()
                .uri(ACCOMMODATION_URI + "?page=0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<AccommodationDto>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);
        Long idFromDatabase = result
                .stream()
                .filter(value-> value.getName().equals(accommodationDto.getName()))
                .map(AccommodationDto::getId)
                .findAny()
                .orElseThrow();

        assertEquals(idFromDatabase,accommodationId);
        accommodationDto.setId(idFromDatabase);
        AccommodationDto accommodationDtoResult = getAccommodation(idFromDatabase);

        assertEquals(accommodationDtoResult.getName(), accommodationDto.getName());

        MQTransferObject<Object> object = null;
        try{
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        Accommodation accommodation = messageMapper.accommodationMessageDtoToEntity(
                rabbitListener.hashMapToAccommodation((LinkedHashMap<?, ?>) object.getMessage()));
                assertEquals(object.getEventType(),"INSERT");
                assertEquals(object.getEntityType(),"Accommodation");
                assertEquals(accommodation,mapper.domainToEntity(mapper.dtoToDomain(accommodationDtoResult)));

    }

    @Test
    void addAccommodationValuesNull(){
        AccommodationDto accommodationDto = AccommodationDto.builder()
                .name(name)
                .description("Hotel close to city center")
                .accommodationType(AccommodationType.HOTEL)
                .build();

        webTestClient.post()
                .uri(ACCOMMODATION_URI)
                .bodyValue(accommodationDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateAccommodation(){
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        List<AccommodationDto> result
                = webTestClient.get()
                .uri(ACCOMMODATION_URI + "?page=0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<AccommodationDto>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);

        MQTransferObject<Object> object = null;
        try{
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        Accommodation accommodation = messageMapper.accommodationMessageDtoToEntity(
                rabbitListener.hashMapToAccommodation((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"INSERT");
        assertEquals(object.getEntityType(),"Accommodation");
        assertEquals(accommodation,mapper.domainToEntity(mapper.dtoToDomain(result.get(0))));

        AccommodationDtoUpdate accommodationDtoUpdate = AccommodationDtoUpdate.builder()
                .id(accommodationId)
                .name(updatedName)
                .description("Hotel close to city center")
                .accommodationType(AccommodationType.HOTEL)
                .build();

        webTestClient.put()
                .uri(ACCOMMODATION_URI)
                .bodyValue(accommodationDtoUpdate)
                .exchange()
                .expectStatus().isOk();

        AccommodationDto accommodationDtoResult = getAccommodation(accommodationId);

        assertEquals(accommodationDtoResult.getName(),accommodationDtoUpdate.getName());


        try{
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        accommodation = messageMapper.accommodationMessageDtoToEntity(
                rabbitListener.hashMapToAccommodation((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"UPDATE");
        assertEquals(object.getEntityType(),"Accommodation");
        assertEquals(accommodation,mapper.domainToEntity(mapper.dtoToDomain(accommodationDtoResult)));


    }

    @Test
    void deleteAccommodation(){
        AmenityDto am = AmenityDto.builder()
                .amenity("Pool")
                .build();
        AmenityDto am2 = AmenityDto.builder()
                .amenity("Terrace")
                .build();
        List<AmenityDto> amenities = new ArrayList<>();
        amenities.add(am);
        amenities.add(am2);

        AccommodationDto accommodationDto = createAccommodation();

        accommodationDto.setAmenities(amenities);

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);
        am.setId(1L);
        am2.setId(2L);

        List<AccommodationDto> result
                = webTestClient.get()
                .uri(ACCOMMODATION_URI + "?page=0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<AccommodationDto>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);

        MQTransferObject<Object> object = null;
        try{
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        Accommodation accommodation = messageMapper.accommodationMessageDtoToEntity(
                rabbitListener.hashMapToAccommodation((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"INSERT");
        assertEquals(object.getEntityType(),"Accommodation");
        assertEquals(accommodation,mapper.domainToEntity(mapper.dtoToDomain(result.get(0))));


        webTestClient.delete()
                .uri(ACCOMMODATION_URI + "/{id}", accommodationId)
                .exchange()
                .expectStatus().isOk();


        try{
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

         accommodation = messageMapper.accommodationMessageDtoToEntity(
                rabbitListener.hashMapToAccommodation((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"DELETE");
        assertEquals(object.getEntityType(),"Accommodation");
        assertEquals(accommodation,mapper.domainToEntity(mapper.dtoToDomain(result.get(0))));


    }

    @Test
    void updateAccommodationThatDoesNotExist(){
       AccommodationDtoUpdate accommodationDtoUpdate = AccommodationDtoUpdate.builder()
               .id(1L)
               .name("Hilton")
               .description("Far from city center")
               .accommodationType(AccommodationType.HOTEL)
               .build();

       webTestClient.put()
               .uri(ACCOMMODATION_URI)
               .bodyValue(accommodationDtoUpdate)
               .exchange()
               .expectStatus().isNotFound();
    }
}
