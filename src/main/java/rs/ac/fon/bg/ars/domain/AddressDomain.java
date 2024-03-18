package rs.ac.fon.bg.ars.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDomain {

    private Long id;

    private AccommodationDomain accommodation;

    private String country;

    private String city;

    private String street;

    private String streetNumber;

    private String postalCode;

    private String latitude;

    private String longitude;

}
