package rs.ac.fon.bg.ars.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressDto {

    private Long id;


    private AccommodationDto accommodation;

    @NotEmpty
    private String country;

    @NotEmpty
    private String city;

    @NotEmpty
    private String street;

    @NotEmpty
    private String streetNumber;

    @NotEmpty
    private String postalCode;

    private String latitude;

    private String longitude;

}
