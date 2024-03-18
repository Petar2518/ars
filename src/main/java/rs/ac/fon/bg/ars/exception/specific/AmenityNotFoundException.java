package rs.ac.fon.bg.ars.exception.specific;

import org.springframework.http.HttpStatus;
import rs.ac.fon.bg.ars.exception.ApiException;

public class AmenityNotFoundException extends ApiException {

    public AmenityNotFoundException(Long id){
        super("Amenity with id: " + id + " doesn't exist", HttpStatus.NOT_FOUND);
    }
}
