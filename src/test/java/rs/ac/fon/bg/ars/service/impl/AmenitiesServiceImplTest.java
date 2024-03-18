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
import rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters.AmenityDomainEntityAdapter;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.AmenityDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.exception.specific.AccommodationNotFoundException;
import rs.ac.fon.bg.ars.exception.specific.AmenityNotFoundException;
import rs.ac.fon.bg.ars.model.AccommodationType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AmenitiesServiceImplTest {
    @Mock
    AmenityDomainEntityAdapter amenityDomainEntityAdapter;

    @InjectMocks
    AmenityServiceImpl amenityService;

    @BeforeEach
    public void setUp(){
        ObjectMapper objectMapper = new ObjectMapper();
        amenityService = new AmenityServiceImpl(amenityDomainEntityAdapter, objectMapper);
    }

    @Test
    void createAmenitySuccessfully(){
        AmenityDomain amenityDomain = AmenityDomain.builder()
                .amenity("Pool")
                .build();

        when(amenityDomainEntityAdapter.save(amenityDomain)).thenReturn(1L);

        ArgumentCaptor<AmenityDomain> captor = ArgumentCaptor.forClass(AmenityDomain.class);

        amenityService.save(amenityDomain);

        verify(amenityDomainEntityAdapter,times(1)).save(captor.capture());

        AmenityDomain capturedAccommodation = captor.getValue();
        assertThat(capturedAccommodation).isEqualTo(amenityDomain);
    }

    @Test
    void findAmenityById(){
        Long id = 1L;

        AmenityDomain amenityDomain = AmenityDomain.builder()
                .id(id)
                .amenity("Pool")
                .build();

        when(amenityDomainEntityAdapter.findById(id)).thenReturn(Optional.of(amenityDomain));

        AmenityDomain actual = amenityService.findById(id);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getAmenity()).isEqualTo("Pool");
    }

    @Test
    void findAmenityWhenIdDoesNotExist(){
        Long id=1L;
        when(amenityDomainEntityAdapter.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(()->amenityService.findById(id))
                .isInstanceOf(AmenityNotFoundException.class)
                .hasMessage("Amenity with id: " + id + " doesn't exist");
    }

    @Test
    void deleteAmenityById(){

        Long id = 1L;

        amenityService.deleteById(id);

        verify(amenityDomainEntityAdapter,times(1)).deleteById(id);
    }

    @Test
    void updateAmenityDetailsSuccessfully(){
        Long id=1L;
        AmenityDomain amenityDomain = AmenityDomain.builder()
                .id(id)
                .amenity("Pool")
                .build();
        AmenityDomain amenityDomainUpdated = AmenityDomain.builder()
                .id(id)
                .amenity("Kid's Pool")
                .build();

        when(amenityDomainEntityAdapter.findById(id)).thenReturn(Optional.of(amenityDomain));

        amenityService.update(amenityDomainUpdated);
        assertThat(amenityDomain.getAmenity()).isEqualTo("Kid's Pool");
        verify(amenityDomainEntityAdapter,times(1)).save(amenityDomain);
    }

    @Test
    void updateAmenityDetailNoExistingId(){

        Long id=1L;
        AmenityDomain amenityDomainUpdated = AmenityDomain.builder()
                .id(id)
                .amenity("Kid's Pool")
                .build();

        when(amenityDomainEntityAdapter.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> amenityService.update(amenityDomainUpdated))
                .isInstanceOf(AmenityNotFoundException.class)
                .hasMessage("Amenity with id: " + id + " doesn't exist");
        verify(amenityDomainEntityAdapter,never()).save(any(AmenityDomain.class));
    }
}
