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
import rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters.AddressDomainEntityAdapter;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.AddressDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.domain.update.AccommodationUnitDomainUpdate;
import rs.ac.fon.bg.ars.domain.update.AddressDomainUpdate;
import rs.ac.fon.bg.ars.exception.specific.AccommodationNotFoundException;
import rs.ac.fon.bg.ars.exception.specific.AccommodationUnitNotFoundException;
import rs.ac.fon.bg.ars.exception.specific.AddressNotFoundException;
import rs.ac.fon.bg.ars.model.AccommodationType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {
    @Mock
    AddressDomainEntityAdapter addressDomainEntityAdapter;
    @Mock
    AccommodationDomainEntityAdapter accommodationDomainEntityAdapter;

    @InjectMocks
    AccommodationServiceImpl accommodationService;

    @InjectMocks
    AddressServiceImpl addressService;

    @BeforeEach
    public void setUp(){
        ObjectMapper objectMapper = new ObjectMapper();
        addressService = new AddressServiceImpl(addressDomainEntityAdapter, objectMapper);
    }

    @Test
    void createAddressSuccessfully(){
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

        AddressDomain addressDomain = AddressDomain.builder()
                .accommodation(accommodationDomain)
                .country("Serbia")
                .city("Belgrade")
                .postalCode("11000")
                .street("Jove Ilica")
                .streetNumber("154")
                .build();


        when(addressDomainEntityAdapter.save(addressDomain)).thenReturn(1L);

        ArgumentCaptor<AddressDomain> captorUnit = ArgumentCaptor.forClass(AddressDomain.class);

        addressService.save(addressDomain);

        verify(addressDomainEntityAdapter,times(1)).save(captorUnit.capture());

        AddressDomain capturedUnit = captorUnit.getValue();
        assertThat(capturedUnit).isEqualTo(addressDomain);
    }

    @Test
    void findAddressById(){
        String name = "Hyatt";
        Long id = 1L;
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .name(name)
                .accommodationType(AccommodationType.APARTMENT)
                .description("Accommodation close to city center")
                .hostId(5L)
                .build();

        AddressDomain addressDomain = AddressDomain.builder()
                .id(id)
                .accommodation(accommodationDomain)
                .country("Serbia")
                .city("Belgrade")
                .postalCode("11000")
                .street("Jove Ilica")
                .streetNumber("154")
                .build();

        when(addressDomainEntityAdapter.findById(id)).thenReturn(Optional.of(addressDomain));


        AddressDomain actualUnit = addressService.findById(id);

        assertThat(actualUnit).isNotNull();
        assertThat(actualUnit.getId()).isEqualTo(id);
        assertThat(actualUnit.getCountry()).isEqualTo("Serbia");
    }

    @Test
    void findAddressWhenIdDoesNotExist(){
        Long id=1L;
        when(addressDomainEntityAdapter.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(()->addressService.findById(id))
                .isInstanceOf(AddressNotFoundException.class)
                .hasMessage("Address with id: " + id + " doesn't exist");
    }

    @Test
    void deleteAddressById(){

        Long id = 1L;

        addressService.deleteById(id);

        verify(addressDomainEntityAdapter,times(1)).deleteById(id);
    }

    @Test
    void updateAddressDetailsSuccessfully() {
        String name = "Hyatt";
        Long id = 1L;
        AccommodationDomain accommodationDomain = AccommodationDomain.builder()
                .name(name)
                .accommodationType(AccommodationType.APARTMENT)
                .description("Accommodation close to city center")
                .hostId(5L)
                .build();

        AddressDomain addressDomain = AddressDomain.builder()
                .id(id)
                .accommodation(accommodationDomain)
                .country("Serbia")
                .city("Belgrade")
                .postalCode("11000")
                .street("Jove Ilica")
                .streetNumber("154")
                .build();
        AddressDomainUpdate addressDomainUpdate = AddressDomainUpdate.builder()
                .id(id)
                .postalCode("11000")
                .street("Jove Markovica")
                .streetNumber("132")
                .build();



        when(addressDomainEntityAdapter.findById(id)).thenReturn(Optional.of(addressDomain));

        addressService.update(addressDomainUpdate);
        assertThat(addressDomain.getStreet()).isEqualTo(addressDomainUpdate.getStreet());
        verify(addressDomainEntityAdapter, times(1)).save(addressDomain);
    }


    @Test
    void updateAddressDetailNoExistingId(){

        String newName = "Big room";
        Long id=1L;
        AddressDomainUpdate addressDomainUpdate = AddressDomainUpdate.builder()
                .id(1L)
                .postalCode("11000")
                .street("Jove Ilica")
                .streetNumber("154")
                .build();

        when(addressDomainEntityAdapter.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> addressService.update(addressDomainUpdate))
                .isInstanceOf(AddressNotFoundException.class)
                .hasMessage("Address with id: " + id + " doesn't exist");
        verify(addressDomainEntityAdapter,never()).save(any(AddressDomain.class));
    }
}
