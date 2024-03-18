package rs.ac.fon.bg.ars.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.fon.bg.ars.constraint.DateConstraint;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="prices")
@DateConstraint
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prices_id_seq")
    @SequenceGenerator(name="prices_id_seq", sequenceName = "prices_id_seq", allocationSize = 1)
    private Long id;

    private BigDecimal amount;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccommodationUnit accommodationUnit;

}
