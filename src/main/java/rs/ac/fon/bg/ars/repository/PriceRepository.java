package rs.ac.fon.bg.ars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.fon.bg.ars.model.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
