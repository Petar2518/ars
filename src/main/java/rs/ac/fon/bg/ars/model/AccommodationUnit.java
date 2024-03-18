package rs.ac.fon.bg.ars.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="accommodation_units")
public class AccommodationUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accommodation_units_id_seq")
    @SequenceGenerator(name="accommodation_units_id_seq", sequenceName = "accommodation_units_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String description;

    private int capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Accommodation accommodation;

    @OneToMany(mappedBy = "accommodationUnit", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Price> prices = new ArrayList<>();

}
