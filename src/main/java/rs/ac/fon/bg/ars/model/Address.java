package rs.ac.fon.bg.ars.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.fon.bg.ars.eventListener.HibernateListener;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="address")
@EntityListeners(HibernateListener.class)

public class Address {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Accommodation accommodation;

    private String country;

    private String city;

    private String street;

    @Column(name="street_number")
    private String streetNumber;

    @Column(name="postal_code")
    private String postalCode;

    @Column(name="coordinate_latitude")
    private String latitude;

    @Column(name="coordinate_longitude")
    private String longitude;

}
