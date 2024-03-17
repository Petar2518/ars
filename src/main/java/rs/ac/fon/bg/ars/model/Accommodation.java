package rs.ac.fon.bg.ars.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
@Table(name="accommodations")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accommodations_id_seq")
    @SequenceGenerator(name="accommodations_id_seq", sequenceName = "accommodations_id_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Column(name="accommodation_type")
    @Enumerated(EnumType.STRING)
    private AccommodationType accommodationType;

    @NotNull
    @Column(name="host_id")
    private Long hostId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="accommodation_amenities",joinColumns = @JoinColumn(name="amenity_id"), inverseJoinColumns = @JoinColumn(name="accommodation_id"))
    private final List<Amenity> amenities = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Image> images = new ArrayList<>();
}
