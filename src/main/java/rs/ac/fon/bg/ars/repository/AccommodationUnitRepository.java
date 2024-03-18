package rs.ac.fon.bg.ars.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.fon.bg.ars.model.AccommodationUnit;

public interface AccommodationUnitRepository extends JpaRepository<AccommodationUnit, Long> {
    Page<AccommodationUnit> findAllByAccommodationId(Long accommodationId, Pageable pageable);
}
