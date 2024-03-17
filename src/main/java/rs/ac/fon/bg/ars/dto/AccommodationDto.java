package rs.ac.fon.bg.ars.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import rs.ac.fon.bg.ars.model.AccommodationType;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationDto {

    private Long id;

    @NotBlank(message = "Name of accommodation must be included")
    @Size(min = 2, message = "Accommodation name must have at least 2 characters")
    private String name;

    private String description;

    @NotNull(message = "Accommodation type must be declared")
    private AccommodationType accommodationType;

    @NotNull(message = "Accommodation must be linked to some host")
    private Long hostId;

    private List<AmenityDto> amenities = new ArrayList<>();
}
