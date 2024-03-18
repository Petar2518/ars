package rs.ac.fon.bg.ars.dto.update;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import rs.ac.fon.bg.ars.dto.AccommodationDto;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressDtoUpdate {

    private Long id;

    @NotEmpty
    private String street;

    @NotEmpty
    private String streetNumber;

    @NotEmpty
    private String postalCode;

    private String latitude;

    private String longitude;

}
