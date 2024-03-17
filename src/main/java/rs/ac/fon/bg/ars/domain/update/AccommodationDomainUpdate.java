package rs.ac.fon.bg.ars.domain.update;

import lombok.*;
import rs.ac.fon.bg.ars.domain.AmenityDomain;
import rs.ac.fon.bg.ars.model.AccommodationType;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationDomainUpdate {
    private Long id;
    private String name;
    private String description;
    private AccommodationType accommodationType;
    private List<AmenityDomain> amenities = new ArrayList<>();

}
