package rs.ac.fon.bg.ars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.fon.bg.ars.model.Accommodation;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}
