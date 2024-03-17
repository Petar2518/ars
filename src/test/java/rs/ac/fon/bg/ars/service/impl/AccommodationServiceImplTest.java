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
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.exception.specific.AccommodationNotFoundException;
import rs.ac.fon.bg.ars.model.AccommodationType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccommodationServiceImplTest {
    @Mock
    AccommodationDomainEntityAdapter accommodationDomainEntityAdapter;

    @InjectMocks
    AccommodationServiceImpl accommodationService;

    @BeforeEach
    public void setUp(){
        ObjectMapper objectMapper = new ObjectMapper();
        accommodationService = new AccommodationServiceImpl(accommodationDomainEntityAdapter, objectMapper);
    }

    @Test
    void createAccommodationSuccessfully(){
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
    }

    @Test
    void findAccommodationById(){
        String name = "Hyatt";
        Long id = 1L;
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .id(1L)
                .name(name)
                .accommodationType(AccommodationType.APARTMENT)
                .description("Accommodation close to city center")
                .hostId(5L)
                .build();

        when(accommodationDomainEntityAdapter.findById(id)).thenReturn(Optional.of(accommodationDomain));

        AccommodationDomain actual = accommodationService.findById(id);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo(name);
    }

    @Test
    void findAccommodationWhenIdDoesNotExist(){
        Long id=1L;
        when(accommodationDomainEntityAdapter.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(()->accommodationService.findById(id))
                .isInstanceOf(AccommodationNotFoundException.class)
                .hasMessage("Accommodation with id: " + id + " doesn't exist");
    }

    @Test
    void deleteAccommodationById(){

        Long id = 1L;

        accommodationService.deleteById(id);

        verify(accommodationDomainEntityAdapter,times(1)).deleteById(id);
    }

    @Test
    void updateAccommodationDetailsSuccessfully(){
        String name = "Hyatt";
        String newName = "Hyatt Belgrade";
        Long id=1L;
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .id(id)
                .name(name)
                .accommodationType(AccommodationType.APARTMENT)
                .description("Accommodation close to city center")
                .hostId(5L)
                .build();
        AccommodationDomainUpdate accommodationDomainUpdate = AccommodationDomainUpdate.builder()
                .id(id)
                .name(newName)
                .accommodationType(AccommodationType.HOTEL)
                .description("Accommodation close to city center")
                .build();

        when(accommodationDomainEntityAdapter.findById(id)).thenReturn(Optional.of(accommodationDomain));

        accommodationService.update(accommodationDomainUpdate);
        assertThat(accommodationDomain.getName()).isEqualTo(newName);
        verify(accommodationDomainEntityAdapter,times(1)).save(accommodationDomain);
    }

    @Test
    void updateAccommodationDetailNoExistingId(){

        String newName = "Hyatt Belgrade";
        Long id=1L;
        AccommodationDomainUpdate accommodationDomainUpdate = AccommodationDomainUpdate.builder()
                .id(id)
                .name(newName)
                .accommodationType(AccommodationType.HOTEL)
                .description("Accommodation close to city center")
                .build();

        when(accommodationDomainEntityAdapter.findById(id)).thenReturn(Optional.empty());

       assertThatThrownBy(()-> accommodationService.update(accommodationDomainUpdate))
               .isInstanceOf(AccommodationNotFoundException.class)
               .hasMessage("Accommodation with id: " + id + " doesn't exist");
       verify(accommodationDomainEntityAdapter,never()).save(any(AccommodationDomain.class));
    }
}
