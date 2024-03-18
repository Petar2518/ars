package rs.ac.fon.bg.ars.dto.update;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rs.ac.fon.bg.ars.dto.AccommodationUnitDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceDtoUpdate {

    private Long id;

    @DecimalMin("0.0")
    private BigDecimal amount;

    @NotNull
    @FutureOrPresent
    private LocalDate dateFrom;

    @NotNull
    @FutureOrPresent
    private LocalDate dateTo;


}