package rs.ac.fon.bg.ars.domain.update;

import lombok.*;
import rs.ac.fon.bg.ars.domain.AccommodationUnitDomain;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceDomainUpdate {


    private Long id;

    private BigDecimal amount;

    private LocalDate dateFrom;
    private LocalDate dateTo;

}