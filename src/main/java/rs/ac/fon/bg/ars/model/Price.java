package rs.ac.fon.bg.ars.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@Entity
@Table(name="prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prices_id_seq")
    @SequenceGenerator(name="prices_id_seq", sequenceName = "prices_id_seq", allocationSize = 1)
    private Long id;

    @DecimalMin("0.0")
    private BigDecimal amount;

    @NotNull
    private LocalDate dateFrom;

    @NotNull
    private LocalDate dateTo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private AccommodationUnit accommodationUnit;

}
