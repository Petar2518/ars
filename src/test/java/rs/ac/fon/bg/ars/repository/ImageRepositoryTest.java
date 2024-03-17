package rs.ac.fon.bg.ars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.Image;
import rs.ac.fon.bg.ars.util.DataJpaTestBase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ImageRepositoryTest extends DataJpaTestBase {

    @Autowired
    AccommodationRepository accommodationRepository;

    @Autowired
    ImageRepository imageRepository;

    @Test
    void saveAndFindById(){
        Accommodation accommodation = Accommodation.builder()
                .name("Apartment 1")
                .accommodationType(AccommodationType.APARTMENT)
                .hostId(5L)
                .build();

        byte[] fileContent = "Testing. . .".getBytes();

        Accommodation accommodationSaved = accommodationRepository.save(accommodation);

        Image image = Image.builder()
                .accommodation(accommodationSaved)
                .image(fileContent)
                .build();
        Image imageSaved = imageRepository.save(image);
        Optional<Image> actual = imageRepository.findById(imageSaved.getId());

        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(imageSaved);
    }

}