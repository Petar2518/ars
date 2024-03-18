package rs.ac.fon.bg.ars.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import rs.ac.fon.bg.ars.model.Accommodation;

import java.sql.Types;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDomain {

    private Long id;

    private byte[] image;

    private AccommodationDomain accommodation;
}
