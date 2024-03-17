package rs.ac.fon.bg.ars.domain;

import lombok.*;
import rs.ac.fon.bg.ars.model.AccommodationType;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationDomain {
    private Long id;
    private String name;
    private String description;
    private AccommodationType accommodationType;
    private Long hostId;
    private List<AmenityDomain> amenities = new ArrayList<>();
}
