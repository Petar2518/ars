package rs.ac.fon.bg.ars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.AccommodationType;
import rs.ac.fon.bg.ars.model.Address;
import rs.ac.fon.bg.ars.util.DataJpaTestBase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddressRepositoryTest extends DataJpaTestBase {

    @Autowired
    AccommodationRepository accommodationRepository;

    @Autowired
    AddressRepository addressRepository;

    @Test
    void saveAndFindById(){
        Accommodation accommodation = Accommodation.builder()
                .name("Apartment 1")
                .accommodationType(AccommodationType.APARTMENT)
                .hostId(5L)
                .build();

        Accommodation accommodationSaved = accommodationRepository.save(accommodation);

        Address address = Address.builder()
                .accommodation(accommodationSaved)
                .country("Serbia")
                .city("Belgrade")
                .street("Jove Ilica")
                .postalCode("11000")
                .streetNumber("154")
                .build();
        Address addressSaved = addressRepository.save(address);
        Optional<Address> actual = addressRepository.findById(addressSaved.getId());

        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(addressSaved);
    }



}