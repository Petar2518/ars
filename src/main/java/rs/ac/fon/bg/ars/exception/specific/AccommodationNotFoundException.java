package rs.ac.fon.bg.ars.exception.specific;

import org.springframework.http.HttpStatus;
import rs.ac.fon.bg.ars.exception.ApiException;

public class AccommodationNotFoundException extends ApiException {
    public AccommodationNotFoundException(Long id){
        super("Accommodation with id: " + id + " doesn't exist", HttpStatus.NOT_FOUND);
    }
}
