package rs.ac.fon.bg.ars.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.fon.bg.ars.model.AccommodationUnit;
import rs.ac.fon.bg.ars.model.Price;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query(value = "SELECT p FROM Price p WHERE p.accommodationUnit = :accommodationUnit " +
            "AND ((p.dateFrom BETWEEN :before AND :after) or " +
            "(p.dateTo BETWEEN :before and :after) or " +
            "(p.dateFrom < :before and p.dateTo > :after))")
    List<Price> findAllAccommodationUnitsInDates(@Param("accommodationUnit") AccommodationUnit accommodationUnit,
                                                 @Param("before") LocalDate before,
                                                 @Param("after") LocalDate after);

    @Query(value = "SELECT p FROM Price p WHERE p.accommodationUnit.id= :accommodationUnit " +
            "AND p.dateFrom> :startDate " +
            "AND p.dateTo < :endDate")
    Page<Price> findAllByAccommodationUnitId(@Param("accommodationUnit")Long accommodationId,
                                             @Param("startDate")LocalDate startDate,
                                             @Param("endDate")LocalDate endDate,
                                             Pageable pageable);
}
