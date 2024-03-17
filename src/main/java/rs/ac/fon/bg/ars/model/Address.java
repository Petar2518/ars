package rs.ac.fon.bg.ars.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
@Table(name="address")
public class Address {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Accommodation accommodation;

    @NotEmpty
    private String country;

    @NotEmpty
    private String city;

    @NotEmpty
    private String street;

    @NotEmpty
    @Column(name="street_number")
    private String streetNumber;

    @NotEmpty
    @Column(name="postal_code")
    private String postalCode;

    @Column(name="coordinate_latitude")
    private String latitude;

    @Column(name="coordinate_longitude")
    private String longitude;

}
