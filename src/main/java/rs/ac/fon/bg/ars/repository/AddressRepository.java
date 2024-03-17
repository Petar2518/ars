package rs.ac.fon.bg.ars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.fon.bg.ars.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
