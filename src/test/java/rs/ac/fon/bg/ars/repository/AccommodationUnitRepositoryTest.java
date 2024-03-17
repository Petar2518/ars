package rs.ac.fon.bg.ars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.AccommodationUnit;
import rs.ac.fon.bg.ars.util.DataJpaTestBase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class AccommodationUnitRepositoryTest extends DataJpaTestBase {

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
        Optional<AccommodationUnit> actual = accommodationUnitRepository.findById(accommodationUnitSaved.getId());

        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(accommodationUnitSaved);
    }
}