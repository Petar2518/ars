package rs.ac.fon.bg.ars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.AccommodationUnit;
import rs.ac.fon.bg.ars.model.Amenity;
import rs.ac.fon.bg.ars.util.DataJpaTestBase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AmenityRepositoryTest extends DataJpaTestBase {


    @Autowired
    AmenityRepository amenityRepository;

    @Test
    void saveAndFindById(){
        Amenity amenity = Amenity.builder()
                .amenity("Pool")
                .build();
        Amenity amenitySaved = amenityRepository.save(amenity);
        Optional<Amenity> actual = amenityRepository.findById(amenitySaved.getId());

        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(amenitySaved);
    }

}