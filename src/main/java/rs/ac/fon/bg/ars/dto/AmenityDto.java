package rs.ac.fon.bg.ars.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmenityDto {
    private Long id;

    @NotEmpty
    private String amenity;
}
