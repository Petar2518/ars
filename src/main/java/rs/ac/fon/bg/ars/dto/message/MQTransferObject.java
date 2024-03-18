package rs.ac.fon.bg.ars.dto.message;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MQTransferObject<T> implements Serializable {
    String eventType;
    String entityType;
    T message;
}
