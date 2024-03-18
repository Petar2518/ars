package rs.ac.fon.bg.ars.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import rs.ac.fon.bg.ars.dto.AccommodationDto;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDto;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDtoUpdate;
import rs.ac.fon.bg.ars.dto.AmenityDto;
import rs.ac.fon.bg.ars.dto.message.MQTransferObject;
import rs.ac.fon.bg.ars.dto.update.AccommodationDtoUpdate;
import rs.ac.fon.bg.ars.mapper.AccommodationMapper;
import rs.ac.fon.bg.ars.mapper.AccommodationUnitMapper;
import rs.ac.fon.bg.ars.mapper.MessageMapper;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.AccommodationUnit;
import rs.ac.fon.bg.ars.util.ComponentTestBase;
import rs.ac.fon.bg.ars.util.RabbitListenerTestComponent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccommodationUnitComponentTest extends ComponentTestBase {

    @Autowired
    AccommodationUnitMapper mapper;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    RabbitListenerTestComponent rabbitListener;

    @Autowired
    WebTestClient webTestClient;

    private final String ACCOMMODATION_URI = "/accommodations";

    String name = "Hyatt Belgrade";
    String unitName = "Room";

    String updatedUnitName = "Big Room";

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

    private AccommodationUnitDto createAccommodationUnit(AccommodationDto accommodationDto){
        return AccommodationUnitDto.builder()
                .name(unitName)
                .accommodation(accommodationDto)
                .capacity(2)
                .description("Room for 2 persons")

                .build();
    }

    private Long postAccommodationUnit(AccommodationUnitDto accommodationUnitDto){
        return webTestClient.post()
                .uri("/rooms")
                .bodyValue(accommodationUnitDto)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Long.class)
                .getResponseBody().toStream().findAny().get();
    }

    private AccommodationUnitDto getAccommodationUnit(Long id){
        return webTestClient.get()
                .uri("/rooms/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccommodationUnitDto.class)
                .returnResult().getResponseBody();
    }


    @Test
    void addAccommodationUnit(){
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AccommodationUnitDto accommodationUnitDto = createAccommodationUnit(accommodationDto);

        Long unitId = postAccommodationUnit(accommodationUnitDto);

        accommodationUnitDto.setId(unitId);




        AccommodationUnitDto accommodationUnitResult = getAccommodationUnit(unitId);

        assertEquals(accommodationUnitDto.getName(),accommodationUnitResult.getName());

        MQTransferObject<Object> object = null;
        try{
            rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        AccommodationUnit accommodationUnit = messageMapper.accommodationUnitMessageDtoToEntity(
                rabbitListener.hashMapToUnit((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"INSERT");
        assertEquals(object.getEntityType(),"AccommodationUnit");
        assertEquals(accommodationUnit,mapper.domainToEntity(mapper.dtoToDomain(accommodationUnitResult)));
    }

    @Test
    void addAccommodationUnitValuesNull(){
        AccommodationUnitDto accommodationUnitDto = AccommodationUnitDto.builder()
                .name(unitName)
                .description("Nice room for 2 persons")
                .capacity(2)
                .build();

        webTestClient.post()
                .uri("/rooms")
                .bodyValue(accommodationUnitDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateAccommodationUnit(){
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AccommodationUnitDto accommodationUnitDto = createAccommodationUnit(accommodationDto);

        Long unitId = postAccommodationUnit(accommodationUnitDto);

        accommodationUnitDto.setId(unitId);


        List<AccommodationUnitDto> result
                = webTestClient.get()
                .uri("/rooms/"+unitId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<AccommodationUnitDto>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);

        MQTransferObject<Object> object = null;
        try{
            rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        AccommodationUnit accommodationUnit = messageMapper.accommodationUnitMessageDtoToEntity(
                rabbitListener.hashMapToUnit((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"INSERT");
        assertEquals(object.getEntityType(),"AccommodationUnit");
        assertEquals(accommodationUnit,mapper.domainToEntity(mapper.dtoToDomain(result.get(0))));

        AccommodationUnitDtoUpdate accommodationUnitDtoUpdate = AccommodationUnitDtoUpdate.builder()
                .id(unitId)
                .name(updatedUnitName)
                .description("Nice room")
                .capacity(5)
                .build();

        webTestClient.put()
                .uri("/rooms")
                .bodyValue(accommodationUnitDtoUpdate)
                .exchange()
                .expectStatus().isOk();

        AccommodationUnitDto accommodationUnitResult = getAccommodationUnit(unitId);


        try{
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        accommodationUnit = messageMapper.accommodationUnitMessageDtoToEntity(
                rabbitListener.hashMapToUnit((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"UPDATE");
        assertEquals(object.getEntityType(),"AccommodationUnit");
        assertEquals(accommodationUnit,mapper.domainToEntity(mapper.dtoToDomain(accommodationUnitResult)));



        assertEquals(accommodationUnitResult.getName(),accommodationUnitDtoUpdate.getName());

    }

    @Test
    void deleteAccommodation(){

        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AccommodationUnitDto accommodationUnitDto = createAccommodationUnit(accommodationDto);

        Long unitId = postAccommodationUnit(accommodationUnitDto);

        accommodationUnitDto.setId(unitId);

        List<AccommodationUnitDto> result
                = webTestClient.get()
                .uri("/rooms/"+unitId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<AccommodationUnitDto>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);

        MQTransferObject<Object> object = null;
        try{
            rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        AccommodationUnit accommodationUnit = messageMapper.accommodationUnitMessageDtoToEntity(
                rabbitListener.hashMapToUnit((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"INSERT");
        assertEquals(object.getEntityType(),"AccommodationUnit");
        assertEquals(accommodationUnit,mapper.domainToEntity(mapper.dtoToDomain(result.get(0))));

        webTestClient.delete()
                .uri("/rooms/{id}", unitId)
                .exchange()
                .expectStatus().isOk();


        try{
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        accommodationUnit = messageMapper.accommodationUnitMessageDtoToEntity(
                rabbitListener.hashMapToUnit((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"DELETE");
        assertEquals(object.getEntityType(),"AccommodationUnit");
        assertEquals(accommodationUnit,mapper.domainToEntity(mapper.dtoToDomain(result.get(0))));

    }

    @Test
    void updateAccommodationThatDoesNotExist(){
        AccommodationDto accommodationDtoUpdate = AccommodationDto.builder()
                .hostId(2L)
                .name("Hilton")
                .description("Far from city center")
                .accommodationType(AccommodationType.HOTEL)
                .build();

        Long accommodationId = postAccommodation(accommodationDtoUpdate);

        accommodationDtoUpdate.setId(accommodationId);

        AccommodationUnitDtoUpdate accommodationUnitDto = AccommodationUnitDtoUpdate.builder()
                .id(0L)
                .name(updatedUnitName)
                .description("Nice room")
                .capacity(5)
                .build();

        webTestClient.put()
                .uri("/rooms")
                .bodyValue(accommodationUnitDto)
                .exchange()
                .expectStatus().isNotFound();
    }
}
