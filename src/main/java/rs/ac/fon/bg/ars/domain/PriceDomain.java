package rs.ac.fon.bg.ars.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rs.ac.fon.bg.ars.model.AccommodationUnit;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceDomain {


    private Long id;

    private BigDecimal amount;

    private LocalDate dateFrom;
    private LocalDate dateTo;

    private AccommodationUnitDomain accommodationUnit;

}

