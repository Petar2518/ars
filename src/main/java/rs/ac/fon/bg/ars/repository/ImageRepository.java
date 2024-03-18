package rs.ac.fon.bg.ars.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.fon.bg.ars.model.Image;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Page<Image> findAllByAccommodationId(Long accommodationId, Pageable pageable);
}
