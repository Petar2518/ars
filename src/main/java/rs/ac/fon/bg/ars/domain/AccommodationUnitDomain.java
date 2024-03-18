package rs.ac.fon.bg.ars.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rs.ac.fon.bg.ars.model.Accommodation;
import rs.ac.fon.bg.ars.model.Price;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationUnitDomain {
    private Long id;

    private String name;

    private String description;

    private int capacity;

    private AccommodationDomain accommodation;

}
