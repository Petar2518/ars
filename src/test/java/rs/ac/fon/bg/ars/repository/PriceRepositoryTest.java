package rs.ac.fon.bg.ars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.AccommodationUnit;
import rs.ac.fon.bg.ars.model.Price;
import rs.ac.fon.bg.ars.util.DataJpaTestBase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PriceRepositoryTest extends DataJpaTestBase {

    @Autowired
    PriceRepository priceRepository;
    @Autowired
    AccommodationRepository accommodationRepository;

    @Autowired
    AccommodationUnitRepository accommodationUnitRepository;

    @Test
    void saveAndFindById(){
        Accommodation accommodation = Accommodation.builder()
                .name("Apartment 1")
                .accommodationType(AccommodationType.APARTMENT)
                .hostId(5L)
                .build();

        Accommodation accommodationSaved = accommodationRepository.save(accommodation);

        AccommodationUnit accommodationUnit = AccommodationUnit.builder()
                .accommodation(accommodationSaved)
                .capacity(2)
                .name("Double room")
                .description("Large quiet room")
                .build();
        AccommodationUnit accommodationUnitSaved = accommodationUnitRepository.save(accommodationUnit);

        LocalDate from = LocalDate.of(2030, Calendar.NOVEMBER,20);
        LocalDate to = LocalDate.of(2030,Calendar.DECEMBER,10);

        Price price = Price.builder()
                .amount(BigDecimal.valueOf(100.00))
                .accommodationUnit(accommodationUnitSaved)
                .dateFrom(from)
                .dateTo(to)
                .build();

        Price priceSaved = priceRepository.save(price);
        Optional<Price> actual = priceRepository.findById(priceSaved.getId());

        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(priceSaved);
    }

}