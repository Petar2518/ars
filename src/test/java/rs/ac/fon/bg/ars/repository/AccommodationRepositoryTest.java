package rs.ac.fon.bg.ars.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.AccommodationUnit;
import rs.ac.fon.bg.ars.util.DataJpaTestBase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


class AccommodationRepositoryTest extends DataJpaTestBase {

    @Autowired
    AccommodationRepository accommodationRepository;

    @Test
    void saveAndFindById(){
        Accommodation accommodation = Accommodation.builder()
                .name("Apartment 1")
                .accommodationType(AccommodationType.APARTMENT)
                .hostId(5L)
                .build();

        Accommodation accommodationSaved = accommodationRepository.save(accommodation);
        Optional<Accommodation> actual = accommodationRepository.findById(accommodationSaved.getId());

        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(accommodationSaved);
    }

}