package rs.ac.fon.bg.ars.exception.specific;

import org.springframework.http.HttpStatus;
import rs.ac.fon.bg.ars.exception.ApiException;

public class AccommodationUnitNotFoundException extends ApiException {
    public AccommodationUnitNotFoundException(Long id) {
        super("Accommodation unit with id: " + id + " doesn't exist", HttpStatus.NOT_FOUND);
    }
}
