package rs.ac.fon.bg.ars.exception.specific;

import org.springframework.http.HttpStatus;
import rs.ac.fon.bg.ars.exception.ApiException;

public class AddressNotFoundException extends ApiException {

    public AddressNotFoundException(Long id){
        super("Address with id: " + id + " doesn't exist", HttpStatus.NOT_FOUND);
    }
}
