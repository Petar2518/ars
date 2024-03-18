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
import rs.ac.fon.bg.ars.dto.PriceDto;
import rs.ac.fon.bg.ars.dto.message.MQTransferObject;
import rs.ac.fon.bg.ars.dto.update.PriceDtoUpdate;
import rs.ac.fon.bg.ars.mapper.AccommodationUnitMapper;
import rs.ac.fon.bg.ars.mapper.MessageMapper;
import rs.ac.fon.bg.ars.mapper.PriceMapper;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.Amenity;
import rs.ac.fon.bg.ars.model.Price;
import rs.ac.fon.bg.ars.util.ComponentTestBase;
import rs.ac.fon.bg.ars.util.RabbitListenerTestComponent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceComponentTest extends ComponentTestBase {

    @Autowired
    PriceMapper mapper;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    RabbitListenerTestComponent rabbitListener;
    @Autowired
    WebTestClient webTestClient;

    private final String ACCOMMODATION_URI = "/accommodations";

    private final String PRICE_URI = "/prices";

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
                .description("Room for 2 persons")
                .capacity(2)
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

    private PriceDto createPrice(AccommodationUnitDto accommodationUnitDto){
        return PriceDto.builder()
                .dateFrom(LocalDate.of(2030,3,24))
                .dateTo(LocalDate.of(2030,3,26))
                .amount(BigDecimal.valueOf(120.00))
                .accommodationUnit(accommodationUnitDto)
                .build();
    }

    private Long postPrice(PriceDto priceDto){
        return webTestClient.post()
                .uri(PRICE_URI)
                .bodyValue(priceDto)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Long.class)
                .getResponseBody().toStream().findAny().get();
    }
    private PriceDto getPriceById(Long id){
        return webTestClient.get()
                .uri(PRICE_URI+"/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PriceDto.class)
                .returnResult().getResponseBody();
    }


    @Test
    void addPrice(){
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AccommodationUnitDto accommodationUnitDto = createAccommodationUnit(accommodationDto);

        Long unitId = postAccommodationUnit(accommodationUnitDto);

        accommodationUnitDto.setId(unitId);

       PriceDto priceDto = createPrice(accommodationUnitDto);

       Long priceId = postPrice(priceDto);

       priceDto.setId(priceId);

       PriceDto priceDtoResult = getPriceById(priceId);

        MQTransferObject<Object> object = null;
        try{
            rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
            rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        Price price = messageMapper.priceMessageDtoToEntity(
                rabbitListener.hashMapToPrice((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"INSERT");
        assertEquals(object.getEntityType(),"Price");
        assertEquals(price,mapper.domainToEntity(mapper.dtoToDomain(priceDtoResult)));

        assertEquals(Objects.requireNonNull(priceDtoResult).getAmount(),priceDto.getAmount());
        assertEquals(Objects.requireNonNull(priceDtoResult).getDateFrom(),priceDto.getDateFrom());
        assertEquals(Objects.requireNonNull(priceDtoResult).getDateTo(),priceDto.getDateTo());
    }

    @Test
    void addPriceInvalidDate(){
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AccommodationUnitDto accommodationUnitDto = createAccommodationUnit(accommodationDto);

        Long unitId = postAccommodationUnit(accommodationUnitDto);

        accommodationUnitDto.setId(unitId);

        PriceDto priceDto = PriceDto.builder()
                .dateFrom(LocalDate.of(2025,1,27))
                .dateTo(LocalDate.of(2025,1,25))
                .amount(BigDecimal.valueOf(50.00))
                .accommodationUnit(accommodationUnitDto)
                .build();

        webTestClient.post()
                .uri(PRICE_URI)
                .bodyValue(priceDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void addPriceValuesNull(){
        PriceDto priceDto = createPrice(null);

        webTestClient.post()
                .uri(PRICE_URI)
                .bodyValue(priceDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updatePrice(){
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AccommodationUnitDto accommodationUnitDto = createAccommodationUnit(accommodationDto);

        Long unitId = postAccommodationUnit(accommodationUnitDto);

        accommodationUnitDto.setId(unitId);

        PriceDto priceDto = createPrice(accommodationUnitDto);

        Long priceId = postPrice(priceDto);

        priceDto.setId(priceId);



        List<PriceDto> result
                = webTestClient.get()
                .uri(PRICE_URI+"/"+unitId+"/price")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<PriceDto>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);

        MQTransferObject<Object> object = null;
        try{
            rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
            rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        Price price = messageMapper.priceMessageDtoToEntity(
                rabbitListener.hashMapToPrice((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"INSERT");
        assertEquals(object.getEntityType(),"Price");
        assertEquals(price,mapper.domainToEntity(mapper.dtoToDomain(result.get(0))));

        PriceDtoUpdate priceDtoUpdate = PriceDtoUpdate.builder()
                .id(priceId)
                .amount(BigDecimal.valueOf(43.00))
                .dateFrom(LocalDate.of(2025,7,21))
                .dateTo(LocalDate.of(2025,7,29))
                .build();

        webTestClient.put()
                .uri(PRICE_URI)
                .bodyValue(priceDtoUpdate)
                .exchange()
                .expectStatus().isOk();

        PriceDto priceResult = getPriceById(priceId);

        try{
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        price = messageMapper.priceMessageDtoToEntity(
                rabbitListener.hashMapToPrice((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"UPDATE");
        assertEquals(object.getEntityType(),"Price");
        assertEquals(price,mapper.domainToEntity(mapper.dtoToDomain(priceResult)));

        assertEquals(Objects.requireNonNull(priceResult).getAmount(),priceDtoUpdate.getAmount());
        assertEquals(Objects.requireNonNull(priceResult).getDateFrom(),priceDtoUpdate.getDateFrom());
        assertEquals(Objects.requireNonNull(priceResult).getDateTo(),priceDtoUpdate.getDateTo());

    }

    @Test
    void deletePrice(){

        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AccommodationUnitDto accommodationUnitDto = createAccommodationUnit(accommodationDto);

        Long unitId = postAccommodationUnit(accommodationUnitDto);

        accommodationUnitDto.setId(unitId);

        PriceDto priceDto = createPrice(accommodationUnitDto);

        Long priceId = postPrice(priceDto);

        priceDto.setId(priceId);

        List<PriceDto> result
                = webTestClient.get()
                .uri(PRICE_URI+"/"+unitId+"/price")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<PriceDto>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);

        MQTransferObject<Object> object = null;
        try{
            rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
            rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        Price price = messageMapper.priceMessageDtoToEntity(
                rabbitListener.hashMapToPrice((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"INSERT");
        assertEquals(object.getEntityType(),"Price");
        assertEquals(price,mapper.domainToEntity(mapper.dtoToDomain(result.get(0))));

        webTestClient.delete()
                .uri(PRICE_URI +"/"+priceId)
                .exchange()
                .expectStatus().isOk();

        try{

            object=rabbitListener.getMqObject().poll(1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        price = messageMapper.priceMessageDtoToEntity(
                rabbitListener.hashMapToPrice((LinkedHashMap<?, ?>) object.getMessage()));
        assertEquals(object.getEventType(),"DELETE");
        assertEquals(object.getEntityType(),"Price");
        assertEquals(price,mapper.domainToEntity(mapper.dtoToDomain(result.get(0))));

    }

    @Test
    void createPriceNewDateBetweenOld(){
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AccommodationUnitDto accommodationUnitDto = createAccommodationUnit(accommodationDto);

        Long unitId = postAccommodationUnit(accommodationUnitDto);

        accommodationUnitDto.setId(unitId);

        PriceDto priceDto = PriceDto.builder()
                .dateFrom(LocalDate.of(2030,2,10))
                .dateTo(LocalDate.of(2030,2,20))
                .amount(BigDecimal.valueOf(50.00))
                .accommodationUnit(accommodationUnitDto)
                .build();

        Long priceId = postPrice(priceDto);

        priceDto.setId(priceId);

        PriceDto priceDtoNew = PriceDto.builder()
                .dateFrom(LocalDate.of(2030,2,13))
                .dateTo(LocalDate.of(2030,2,17))
                .amount(BigDecimal.valueOf(70.00))
                .accommodationUnit(accommodationUnitDto)
                .build();

        webTestClient.post()
                .uri(PRICE_URI)
                .bodyValue(priceDtoNew)
                .exchange()
                .expectStatus().isBadRequest();

        List<PriceDto> result
                = webTestClient.get()
                .uri(PRICE_URI+"/"+unitId+"/price")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<PriceDto>() {
                })
                .returnResult()
                .getResponseBody();


        assertEquals(result.size(),1);
        assertEquals(result.get(0).getDateFrom(), LocalDate.of(2030,2,10));
        assertEquals(result.get(0).getDateTo(), LocalDate.of(2030,2,20));
        assertEquals(result.get(0).getAmount(), BigDecimal.valueOf(50.00));
    }

    @Test
    void createPriceOldEndsAfterNewStarts(){
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AccommodationUnitDto accommodationUnitDto = createAccommodationUnit(accommodationDto);

        Long unitId = postAccommodationUnit(accommodationUnitDto);

        accommodationUnitDto.setId(unitId);

        PriceDto priceDto = PriceDto.builder()
                .dateFrom(LocalDate.of(2030,2,10))
                .dateTo(LocalDate.of(2030,2,20))
                .amount(BigDecimal.valueOf(50.00))
                .accommodationUnit(accommodationUnitDto)
                .build();

        Long priceId = postPrice(priceDto);

        priceDto.setId(priceId);

        PriceDto priceDtoNew = PriceDto.builder()
                .dateFrom(LocalDate.of(2030,2,13))
                .dateTo(LocalDate.of(2030,2,25))
                .amount(BigDecimal.valueOf(70.00))
                .accommodationUnit(accommodationUnitDto)
                .build();

        webTestClient.post()
                .uri(PRICE_URI)
                .bodyValue(priceDtoNew)
                .exchange()
                .expectStatus().isBadRequest();

        List<PriceDto> result
                = webTestClient.get()
                .uri(PRICE_URI+"/"+unitId+"/price")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<PriceDto>() {
                })
                .returnResult()
                .getResponseBody();


        assertEquals(result.size(),1);
        assertEquals(result.get(0).getDateFrom(), LocalDate.of(2030,2,10));
        assertEquals(result.get(0).getDateTo(), LocalDate.of(2030,2,20));
        assertEquals(result.get(0).getAmount(), BigDecimal.valueOf(50.00));
    }

    @Test
    void createPriceOldStartsBeforeNewEnds(){
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AccommodationUnitDto accommodationUnitDto = createAccommodationUnit(accommodationDto);

        Long unitId = postAccommodationUnit(accommodationUnitDto);

        accommodationUnitDto.setId(unitId);

        PriceDto priceDto = PriceDto.builder()
                .dateFrom(LocalDate.of(2030,2,10))
                .dateTo(LocalDate.of(2030,2,20))
                .amount(BigDecimal.valueOf(50.00))
                .accommodationUnit(accommodationUnitDto)
                .build();

        Long priceId = postPrice(priceDto);

        priceDto.setId(priceId);

        PriceDto priceDtoNew = PriceDto.builder()
                .dateFrom(LocalDate.of(2030,2,1))
                .dateTo(LocalDate.of(2030,2,17))
                .amount(BigDecimal.valueOf(70.00))
                .accommodationUnit(accommodationUnitDto)
                .build();

        webTestClient.post()
                .uri(PRICE_URI)
                .bodyValue(priceDtoNew)
                .exchange()
                .expectStatus().isBadRequest();

        List<PriceDto> result
                = webTestClient.get()
                .uri(PRICE_URI+"/"+unitId+"/price")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<PriceDto>() {
                })
                .returnResult()
                .getResponseBody();


        assertEquals(result.size(),1);
        assertEquals(result.get(0).getDateFrom(), LocalDate.of(2030,2,10));
        assertEquals(result.get(0).getDateTo(), LocalDate.of(2030,2,20));
        assertEquals(result.get(0).getAmount(), BigDecimal.valueOf(50.00));
    }

    @Test
    void createPriceTwoOldInsideOfNewOne(){
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AccommodationUnitDto accommodationUnitDto = createAccommodationUnit(accommodationDto);

        Long unitId = postAccommodationUnit(accommodationUnitDto);

        accommodationUnitDto.setId(unitId);

        PriceDto priceDto = PriceDto.builder()
                .dateFrom(LocalDate.of(2030,2,10))
                .dateTo(LocalDate.of(2030,2,20))
                .amount(BigDecimal.valueOf(50.00))
                .accommodationUnit(accommodationUnitDto)
                .build();

        Long priceId = postPrice(priceDto);

        priceDto.setId(priceId);

        PriceDto priceDtoSecond = PriceDto.builder()
                .dateFrom(LocalDate.of(2030,2,1))
                .dateTo(LocalDate.of(2030,2,9))
                .amount(BigDecimal.valueOf(30.00))
                .accommodationUnit(accommodationUnitDto)
                .build();

        Long priceId2 = postPrice(priceDtoSecond);


        priceDtoSecond.setId(priceId2);

        PriceDto priceDtoNew = PriceDto.builder()
                .dateFrom(LocalDate.of(2030,2,1))
                .dateTo(LocalDate.of(2030,2,20))
                .amount(BigDecimal.valueOf(70.00))
                .accommodationUnit(accommodationUnitDto)
                .build();

        webTestClient.post()
                .uri(PRICE_URI)
                .bodyValue(priceDtoNew)
                .exchange()
                .expectStatus().isBadRequest();

        List<PriceDto> result
                = webTestClient.get()
                .uri(PRICE_URI+"/"+unitId+"/price")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<PriceDto>() {
                })
                .returnResult()
                .getResponseBody();


        assertEquals(result.size(),2);
        assertEquals(result.get(0).getDateFrom(), LocalDate.of(2030,2,10));
        assertEquals(result.get(0).getDateTo(), LocalDate.of(2030,2,20));
        assertEquals(result.get(0).getAmount(), BigDecimal.valueOf(50.00));
        assertEquals(result.get(1).getDateFrom(), LocalDate.of(2030,2,1));
        assertEquals(result.get(1).getDateTo(), LocalDate.of(2030,2,9));
        assertEquals(result.get(1).getAmount(), BigDecimal.valueOf(30.00));
    }

    @Test
    void createPricesDateBeforeToday(){
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AccommodationUnitDto accommodationUnitDto = createAccommodationUnit(accommodationDto);

        Long unitId = postAccommodationUnit(accommodationUnitDto);

        accommodationUnitDto.setId(unitId);

        PriceDto priceDto = PriceDto.builder()
                .dateFrom(LocalDate.of(2020,2,10))
                .dateTo(LocalDate.of(2020,2,20))
                .amount(BigDecimal.valueOf(50.00))
                .accommodationUnit(accommodationUnitDto)
                .build();


        webTestClient.post()
                .uri(PRICE_URI)
                .bodyValue(priceDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updatePriceThatDoesNotExist(){
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        AccommodationUnitDto accommodationUnitDto = createAccommodationUnit(accommodationDto);

        Long unitId = postAccommodationUnit(accommodationUnitDto);

        accommodationUnitDto.setId(unitId);

        PriceDtoUpdate priceDtoUpdate = PriceDtoUpdate.builder()
                .id(0L)
                .dateFrom(LocalDate.of(2025,12,2))
                .dateTo(LocalDate.of(2025,12,3))
                .amount(BigDecimal.valueOf(65.00))
                .build();

        webTestClient.put()
                .uri(PRICE_URI)
                .bodyValue(priceDtoUpdate)
                .exchange()
                .expectStatus().isNotFound();
    }
}
