package rs.ac.fon.bg.ars.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
@Table(name="amenities")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "amenities_id_seq")
    @SequenceGenerator(name="amenities_id_seq", sequenceName = "amenities_id_seq", allocationSize = 1)
    private Long id;

    @NotEmpty
    private String amenity;

    @ManyToMany(mappedBy = "amenities")
    private final List<Accommodation> accommodations = new ArrayList<>();
}
