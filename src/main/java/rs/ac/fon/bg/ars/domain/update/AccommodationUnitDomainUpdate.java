package rs.ac.fon.bg.ars.domain.update;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationUnitDomainUpdate {
    private Long id;

    private String name;

    private String description;

    private int capacity;


}