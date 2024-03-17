package rs.ac.fon.bg.ars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.fon.bg.ars.model.Amenity;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}
