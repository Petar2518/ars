package rs.ac.fon.bg.ars.dto.message;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceMessageDto implements Serializable {
    private Long id;
    private BigDecimal amount;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private AccommodationUnitMessageDto accommodationUnit;
}
