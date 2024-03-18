package rs.ac.fon.bg.ars.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters.AccommodationDomainEntityAdapter;
import rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters.AccommodationUnitDomainEntityAdapter;
import rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters.ImageDomainEntityAdapter;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.ImageDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.domain.update.AccommodationUnitDomainUpdate;
import rs.ac.fon.bg.ars.exception.specific.AccommodationNotFoundException;
import rs.ac.fon.bg.ars.exception.specific.AccommodationUnitNotFoundException;
import rs.ac.fon.bg.ars.exception.specific.ImageNotFoundException;
import rs.ac.fon.bg.ars.model.AccommodationType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTest {
    @Mock
    ImageDomainEntityAdapter imageDomainEntityAdapter;
    @Mock
    AccommodationDomainEntityAdapter accommodationDomainEntityAdapter;

    @InjectMocks
    AccommodationServiceImpl accommodationService;

    @InjectMocks
    ImageServiceImpl imageService;


    @Test
    void createImageSuccessfully(){
        String name = "Hyatt";
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .name(name)
                .accommodationType(AccommodationType.APARTMENT)
                .description("Accommodation close to city center")
                .hostId(5L)
                .build();

        when(accommodationDomainEntityAdapter.save(accommodationDomain)).thenReturn(1L);

        ArgumentCaptor<AccommodationDomain> captor = ArgumentCaptor.forClass(AccommodationDomain.class);

        accommodationService.save(accommodationDomain);

        verify(accommodationDomainEntityAdapter,times(1)).save(captor.capture());

        AccommodationDomain capturedAccommodation = captor.getValue();
        assertThat(capturedAccommodation).isEqualTo(accommodationDomain);

        ImageDomain imageDomain = ImageDomain.builder()
                .accommodation(accommodationDomain)
                .image("Testing. . .".getBytes())
                .build();

        when(imageDomainEntityAdapter.save(imageDomain)).thenReturn(1L);

        ArgumentCaptor<ImageDomain> captorUnit = ArgumentCaptor.forClass(ImageDomain.class);

        imageService.save(imageDomain);

        verify(imageDomainEntityAdapter,times(1)).save(captorUnit.capture());

        ImageDomain capturedUnit = captorUnit.getValue();
        assertThat(capturedUnit).isEqualTo(imageDomain);
    }

    @Test
    void findImageById(){
        String name = "Hyatt";
        Long id = 1L;
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .name(name)
                .accommodationType(AccommodationType.APARTMENT)
                .description("Accommodation close to city center")
                .hostId(5L)
                .build();

        ImageDomain imageDomain = ImageDomain.builder()
                .id(id)
                .accommodation(accommodationDomain)
                .image("Testing. . .".getBytes())
                .build();

        when(imageDomainEntityAdapter.findById(id)).thenReturn(Optional.of(imageDomain));


        ImageDomain actualUnit = imageService.findById(id);

        assertThat(actualUnit).isNotNull();
        assertThat(actualUnit.getId()).isEqualTo(id);
        assertThat(actualUnit.getImage()).isEqualTo("Testing. . .".getBytes());
    }

    @Test
    void findImageWhenIdDoesNotExist(){
        Long id=1L;
        when(imageDomainEntityAdapter.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(()->imageService.findById(id))
                .isInstanceOf(ImageNotFoundException.class)
                .hasMessage("Image with id: " + id + " doesn't exist");
    }

    @Test
    void deleteImageUnitById(){

        Long id = 1L;

        imageService.deleteById(id);

        verify(imageDomainEntityAdapter,times(1)).deleteById(id);
    }


}
