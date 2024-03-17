package rs.ac.fon.bg.ars.domain;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmenityDomain {
    private Long id;
    private String amenity;
}
