package rs.ac.fon.bg.ars.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImageDto {


    private Long id;

    @NotNull
    private byte[] image;

    @NotNull
    private AccommodationDto accommodation;
}
