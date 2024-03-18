package rs.ac.fon.bg.ars.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.fon.bg.ars.eventListener.HibernateListener;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="amenities")
@EntityListeners(HibernateListener.class)

public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "amenities_id_seq")
    @SequenceGenerator(name="amenities_id_seq", sequenceName = "amenities_id_seq", allocationSize = 1)
    private Long id;

    private String amenity;

    @ManyToMany(mappedBy = "amenities")
    private final List<Accommodation> accommodations = new ArrayList<>();
}
