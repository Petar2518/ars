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
import rs.ac.fon.bg.ars.mapper.AmenityMapper;
import rs.ac.fon.bg.ars.mapper.MessageMapper;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.Address;
import rs.ac.fon.bg.ars.model.Amenity;
import rs.ac.fon.bg.ars.util.ComponentTestBase;
import rs.ac.fon.bg.ars.util.RabbitListenerTestComponent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmenityComponentTest extends ComponentTestBase {

    @Autowired
    AmenityMapper mapper;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    RabbitListenerTestComponent rabbitListener;

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

        MQTransferObject<Object> object = null;
        try{
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        Amenity amenity = messageMapper.amenityMessageDtoToEntity(
                rabbitListener.hashMapToAmenity((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"INSERT");
        assertEquals(object.getEntityType(),"Amenity");
        assertEquals(amenity,mapper.domainToEntity(mapper.dtoToDomain(result.get(0))));
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

        MQTransferObject<Object> object = null;
        try{
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        Amenity amenity = messageMapper.amenityMessageDtoToEntity(
                rabbitListener.hashMapToAmenity((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"INSERT");
        assertEquals(object.getEntityType(),"Amenity");
        assertEquals(amenity,mapper.domainToEntity(mapper.dtoToDomain(result.get(0))));

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

        try{
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        amenity = messageMapper.amenityMessageDtoToEntity(
                rabbitListener.hashMapToAmenity((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"UPDATE");
        assertEquals(object.getEntityType(),"Amenity");
        assertEquals(amenity,mapper.domainToEntity(mapper.dtoToDomain(amenityDtoResult)));

    }

    @Test
    void deleteAmenity(){

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

        MQTransferObject<Object> object = null;
        try{
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        Amenity amenity = messageMapper.amenityMessageDtoToEntity(
                rabbitListener.hashMapToAmenity((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"INSERT");
        assertEquals(object.getEntityType(),"Amenity");
        assertEquals(amenity,mapper.domainToEntity(mapper.dtoToDomain(result.get(0))));


        webTestClient.delete()
                .uri(AMENITY_URI + "/{id}", amenityId)
                .exchange()
                .expectStatus().isOk();

        try{
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        amenity = messageMapper.amenityMessageDtoToEntity(
                rabbitListener.hashMapToAmenity((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"DELETE");
        assertEquals(object.getEntityType(),"Amenity");
        assertEquals(amenity,mapper.domainToEntity(mapper.dtoToDomain(result.get(0))));

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
