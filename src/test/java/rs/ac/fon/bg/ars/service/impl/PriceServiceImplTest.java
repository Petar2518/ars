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
import rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters.PriceDomainEntityAdapter;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;
import rs.ac.fon.bg.ars.domain.PriceDomain;
import rs.ac.fon.bg.ars.domain.update.AccommodationDomainUpdate;
import rs.ac.fon.bg.ars.domain.update.AccommodationUnitDomainUpdate;
import rs.ac.fon.bg.ars.domain.update.PriceDomainUpdate;
import rs.ac.fon.bg.ars.exception.specific.AccommodationNotFoundException;
import rs.ac.fon.bg.ars.exception.specific.AccommodationUnitNotFoundException;
import rs.ac.fon.bg.ars.exception.specific.DateUnavailableException;
import rs.ac.fon.bg.ars.exception.specific.PriceNotFoundException;
import rs.ac.fon.bg.ars.model.AccommodationType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PriceServiceImplTest {
    @Mock
    AccommodationUnitDomainEntityAdapter accommodationUnitDomainEntityAdapter;
    @Mock
    AccommodationDomainEntityAdapter accommodationDomainEntityAdapter;

    @Mock
    PriceDomainEntityAdapter priceDomainEntityAdapter;

    @InjectMocks
    PriceServiceImpl priceService;

    @InjectMocks
    AccommodationServiceImpl accommodationService;

    @InjectMocks
    AccommodationUnitServiceImpl accommodationUnitService;

    @BeforeEach
    public void setUp(){
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        priceService = new PriceServiceImpl(priceDomainEntityAdapter, objectMapper);
    }

    @Test
    void createPriceSuccessfully(){
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

        PriceDomain priceDomain = PriceDomain.builder()
                .dateFrom(LocalDate.of(2025,3,24))
                .dateTo(LocalDate.of(2025,3,26))
                .amount(BigDecimal.valueOf(110.00))
                .accommodationUnit(accommodationUnitDomain)
                .build();

        when(priceDomainEntityAdapter.save(priceDomain)).thenReturn(1L);

        ArgumentCaptor<PriceDomain> captorPrice = ArgumentCaptor.forClass(PriceDomain.class);

        priceService.save(priceDomain);

        verify(priceDomainEntityAdapter,times(1)).save(captorPrice.capture());

        PriceDomain capturedPrice = captorPrice.getValue();

        assertThat(capturedPrice).isEqualTo(priceDomain);
    }

    @Test
    void createPriceForOccupiedDate(){
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

        PriceDomain priceDomain = PriceDomain.builder()
                .dateFrom(LocalDate.of(2025,3,22))
                .dateTo(LocalDate.of(2025,3,26))
                .amount(BigDecimal.valueOf(110.00))
                .accommodationUnit(accommodationUnitDomain)
                .build();

        when(priceDomainEntityAdapter.save(priceDomain)).thenReturn(1L);

        ArgumentCaptor<PriceDomain> captorPrice = ArgumentCaptor.forClass(PriceDomain.class);

        priceService.save(priceDomain);

        verify(priceDomainEntityAdapter,times(1)).save(captorPrice.capture());

        PriceDomain capturedPrice = captorPrice.getValue();


        assertThat(capturedPrice).isEqualTo(priceDomain);
        LocalDate from = LocalDate.of(2025,3,24);
        LocalDate to = LocalDate.of(2025,3,25);

        List<PriceDomain> priceDomainList = new ArrayList<>();
        priceDomainList.add(priceDomain);
        PriceDomain priceDomain2 = PriceDomain.builder()
                .dateFrom(from)
                .dateTo(to)
                .amount(BigDecimal.valueOf(110.00))
                .accommodationUnit(accommodationUnitDomain)
                .build();

        when(priceDomainEntityAdapter.findUnavailableDates(accommodationUnitDomain,from,to)).thenReturn(priceDomainList);

        assertThatThrownBy(()-> priceService.save(priceDomain2))
                .isInstanceOf(DateUnavailableException.class)
                .hasMessage("Price is already added for that date");
    }

    @Test
    void findPriceById(){
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

        PriceDomain priceDomain = PriceDomain.builder()
                .id(id)
                .dateFrom(LocalDate.of(2025,3,22))
                .dateTo(LocalDate.of(2025,3,26))
                .amount(BigDecimal.valueOf(110.00))
                .accommodationUnit(accommodationUnitDomain)
                .build();

        when(priceDomainEntityAdapter.findById(id)).thenReturn(Optional.of(priceDomain));


        PriceDomain actualUnit = priceService.findById(id);

        assertThat(actualUnit).isNotNull();
        assertThat(actualUnit.getId()).isEqualTo(id);
        assertThat(actualUnit.getAmount()).isEqualTo(BigDecimal.valueOf(110.00));
    }

    @Test
    void findPriceWhenIdDoesNotExist(){
        Long id=1L;
        when(priceDomainEntityAdapter.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(()->priceService.findById(id))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessage("Price with id: " + id + " doesn't exist");
    }

    @Test
    void deletePriceById(){

        Long id = 1L;

        priceService.deleteById(id);

        verify(priceDomainEntityAdapter,times(1)).deleteById(id);
    }

    @Test
    void updatePriceDetailsSuccessfully() {
        String name = "Hyatt";
        String unitName = "Room";
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

        PriceDomain priceDomain = PriceDomain.builder()
                .id(id)
                .dateFrom(LocalDate.of(2025,3,22))
                .dateTo(LocalDate.of(2025,3,26))
                .amount(BigDecimal.valueOf(110.00))
                .accommodationUnit(accommodationUnitDomain)
                .build();

        PriceDomainUpdate priceDomainUpdate = PriceDomainUpdate.builder()
                .id(id)
                .dateFrom(LocalDate.of(2025,4,22))
                .dateTo(LocalDate.of(2025,4,26))
                .amount(BigDecimal.valueOf(120.00))
                .build();

        when(priceDomainEntityAdapter.findById(id)).thenReturn(Optional.of(priceDomain));

        priceService.update(priceDomainUpdate);
        assertThat(priceDomain.getAmount()).isEqualTo(BigDecimal.valueOf(120.00));
        assertThat(priceDomain.getDateFrom()).isEqualTo(LocalDate.of(2025,4,22));
        assertThat(priceDomain.getDateTo()).isEqualTo(LocalDate.of(2025,4,26));
        verify(priceDomainEntityAdapter, times(1)).save(priceDomain);
    }


    @Test
    void updatePriceNoExistingId(){

        String newName = "Big room";
        Long id=1L;
        PriceDomainUpdate priceDomainUpdate = PriceDomainUpdate.builder()
                .id(id)
                .dateFrom(LocalDate.of(2025,4,22))
                .dateTo(LocalDate.of(2025,4,26))
                .amount(BigDecimal.valueOf(120.00))
                .build();

        when(priceDomainEntityAdapter.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> priceService.update(priceDomainUpdate))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessage("Price with id: " + id + " doesn't exist");
        verify(priceDomainEntityAdapter,never()).save(any(PriceDomain.class));
    }
}
