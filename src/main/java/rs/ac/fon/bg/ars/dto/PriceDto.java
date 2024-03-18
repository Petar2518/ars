package rs.ac.fon.bg.ars.dto;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PriceDto {

    private Long id;

    @DecimalMin("0.0")
    private BigDecimal amount;

    @NotNull
    @FutureOrPresent
    private LocalDate dateFrom;

    @NotNull
    @FutureOrPresent
    private LocalDate dateTo;

    @NotNull
    private AccommodationUnitDto accommodationUnit;

}
