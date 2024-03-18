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
import rs.ac.fon.bg.ars.mapper.ImageMapper;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.AccommodationUnit;
import rs.ac.fon.bg.ars.util.ComponentTestBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImageComponentTest extends ComponentTestBase {

    @Autowired
    ImageMapper mapper;

    @Autowired
    WebTestClient webTestClient;

    private final String IMAGE_URI = "/images";

    String name = "Hyatt Belgrade";

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

    private ImageDto createImage(AccommodationDto accommodationDto){
        return ImageDto.builder()
                .image("Testing. . . ".getBytes())
                .accommodation(accommodationDto)
                .build();
    }

    private Long postImage(ImageDto imageDto){
        return webTestClient.post()
                .uri(IMAGE_URI)
                .bodyValue(imageDto)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Long.class)
                .getResponseBody().toStream().findAny().get();
    }

    private ImageDto getImage(Long id){
        return webTestClient.get()
                .uri(IMAGE_URI+"/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ImageDto.class)
                .returnResult().getResponseBody();
    }


    @Test
    void addImage() {
        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        ImageDto imageDto = createImage(accommodationDto);

        Long imageId = postImage(imageDto);

        imageDto.setId(imageId);

        ImageDto imageDtoResult = getImage(imageId);

        assertEquals(Arrays.toString(imageDtoResult.getImage()),Arrays.toString(imageDto.getImage()));
        assertEquals(imageDtoResult.getAccommodation().getId(),imageDto.getAccommodation().getId());
    }
    @Test
    void addImageValuesNull(){
        ImageDto imageDto = ImageDto.builder()
                .image("Testing. . .".getBytes())
                .build();

        webTestClient.post()
                .uri(IMAGE_URI)
                .bodyValue(imageDto)
                .exchange()
                .expectStatus().isBadRequest();
    }


    @Test
    void deleteImage(){

        AccommodationDto accommodationDto = createAccommodation();

        Long accommodationId = postAccommodation(accommodationDto);

        accommodationDto.setId(accommodationId);

        ImageDto imageDto = createImage(accommodationDto);

        Long imageId = postImage(imageDto);

        imageDto.setId(imageId);

        ImageDto imageDtoResult = getImage(imageId);

        assertEquals(Arrays.toString(imageDtoResult.getImage()),Arrays.toString(imageDto.getImage()));
        assertEquals(imageDtoResult.getAccommodation().getId(),imageDto.getAccommodation().getId());

        webTestClient.delete()
                .uri(IMAGE_URI+"/{id}", imageId)
                .exchange()
                .expectStatus().isOk();

    }
}
