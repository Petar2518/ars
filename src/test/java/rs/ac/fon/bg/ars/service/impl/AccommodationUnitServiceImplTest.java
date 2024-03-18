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
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.domain.update.AccommodationUnitDomainUpdate;
import rs.ac.fon.bg.ars.exception.specific.AccommodationNotFoundException;
import rs.ac.fon.bg.ars.exception.specific.AccommodationUnitNotFoundException;
import rs.ac.fon.bg.ars.model.AccommodationType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccommodationUnitServiceImplTest {
    @Mock
    AccommodationUnitDomainEntityAdapter accommodationUnitDomainEntityAdapter;
    @Mock
    AccommodationDomainEntityAdapter accommodationDomainEntityAdapter;

    @InjectMocks
    AccommodationServiceImpl accommodationService;

    @InjectMocks
    AccommodationUnitServiceImpl accommodationUnitService;

    @BeforeEach
    public void setUp(){
        ObjectMapper objectMapper = new ObjectMapper();
        accommodationUnitService = new AccommodationUnitServiceImpl(accommodationUnitDomainEntityAdapter, objectMapper);
    }

    @Test
    void createAccommodationUnitSuccessfully(){
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

        AccommodationUnitDomain accommodationUnitDomain = AccommodationUnitDomain.builder()
                .accommodation(accommodationDomain)
                .name("Double room")
                .description("Nice room")
                .capacity(2)
                .build();

        when(accommodationUnitDomainEntityAdapter.save(accommodationUnitDomain)).thenReturn(1L);

        ArgumentCaptor<AccommodationUnitDomain> captorUnit = ArgumentCaptor.forClass(AccommodationUnitDomain.class);

        accommodationUnitService.save(accommodationUnitDomain);

        verify(accommodationUnitDomainEntityAdapter,times(1)).save(captorUnit.capture());

        AccommodationUnitDomain capturedUnit = captorUnit.getValue();
        assertThat(capturedUnit).isEqualTo(accommodationUnitDomain);
    }

    @Test
    void findAccommodationUnitById(){
        String name = "Hyatt";
        String unitName = "Double room";
        Long id = 1L;
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .name(name)
                .accommodationType(AccommodationType.APARTMENT)
                .description("Accommodation close to city center")
                .hostId(5L)
                .build();

        AccommodationUnitDomain accommodationUnitDomain = AccommodationUnitDomain.builder()
                .id(id)
                .accommodation(accommodationDomain)
                .name("Double room")
                .description("Nice room")
                .capacity(2)
                .build();

        when(accommodationUnitDomainEntityAdapter.findById(id)).thenReturn(Optional.of(accommodationUnitDomain));


        AccommodationUnitDomain actualUnit = accommodationUnitService.findById(id);

        assertThat(actualUnit).isNotNull();
        assertThat(actualUnit.getId()).isEqualTo(id);
        assertThat(actualUnit.getName()).isEqualTo(unitName);
    }

    @Test
    void findAccommodationUnitWhenIdDoesNotExist(){
        Long id=1L;
        when(accommodationUnitDomainEntityAdapter.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(()->accommodationUnitService.findById(id))
                .isInstanceOf(AccommodationUnitNotFoundException.class)
                .hasMessage("Accommodation unit with id: " + id + " doesn't exist");
    }

    @Test
    void deleteAccommodationUnitById(){

        Long id = 1L;

        accommodationUnitService.deleteById(id);

        verify(accommodationUnitDomainEntityAdapter,times(1)).deleteById(id);
    }

    @Test
    void updateAccommodationUnitDetailsSuccessfully() {
        String name = "Hyatt";
        String unitName = "Room";
        String newUnitName = "New room";
        Long id = 1L;
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .name(name)
                .accommodationType(AccommodationType.APARTMENT)
                .description("Accommodation close to city center")
                .hostId(5L)
                .build();

        AccommodationUnitDomain accommodationUnitDomain = AccommodationUnitDomain.builder()
                .id(id)
                .accommodation(accommodationDomain)
                .name(unitName)
                .description("Nice room")
                .capacity(2)
                .build();

        AccommodationUnitDomainUpdate accommodationUnitDomainUpdate = AccommodationUnitDomainUpdate.builder()
                .id(id)
                .name(newUnitName)
                .description("New room")
                .capacity(3)
                .build();

        when(accommodationUnitDomainEntityAdapter.findById(id)).thenReturn(Optional.of(accommodationUnitDomain));

        accommodationUnitService.update(accommodationUnitDomainUpdate);
        assertThat(accommodationUnitDomain.getName()).isEqualTo(newUnitName);
        verify(accommodationUnitDomainEntityAdapter, times(1)).save(accommodationUnitDomain);
    }


    @Test
    void updateAccommodationUnitDetailNoExistingId(){

        String newName = "Big room";
        Long id=1L;
        AccommodationUnitDomainUpdate accommodationUnitDomainUpdate = AccommodationUnitDomainUpdate.builder()
                .id(id)
                .name(newName)
                .description("New room")
                .capacity(3)
                .build();

        when(accommodationUnitDomainEntityAdapter.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> accommodationUnitService.update(accommodationUnitDomainUpdate))
                .isInstanceOf(AccommodationUnitNotFoundException.class)
                .hasMessage("Accommodation unit with id: " + id + " doesn't exist");
        verify(accommodationUnitDomainEntityAdapter,never()).save(any(AccommodationUnitDomain.class));
    }
}
