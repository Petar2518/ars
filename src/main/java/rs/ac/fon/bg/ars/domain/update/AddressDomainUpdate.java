package rs.ac.fon.bg.ars.domain.update;

import lombok.*;
import rs.ac.fon.bg.ars.domain.AccommodationDomain;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDomainUpdate {

    private Long id;

    private String street;

    private String streetNumber;

    private String postalCode;

    private String latitude;

    private String longitude;

}
