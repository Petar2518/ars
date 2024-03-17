package rs.ac.fon.bg.ars.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
@Table(name="accommodation_units")
public class AccommodationUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accommodation_units_id_seq")
    @SequenceGenerator(name="accommodation_units_id_seq", sequenceName = "accommodation_units_id_seq", allocationSize = 1)
    private Long id;

    @NotEmpty
    private String name;

    private String description;

    private int capacity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Accommodation accommodation;

    @OneToMany(mappedBy = "accommodationUnit", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Price> prices = new ArrayList<>();

}
