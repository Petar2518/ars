package rs.ac.fon.bg.ars.exception.specific;

import org.springframework.http.HttpStatus;
import rs.ac.fon.bg.ars.exception.ApiException;

public class PriceNotFoundException extends ApiException {
    public PriceNotFoundException(Long id){
        super("Price with id: " + id + " doesn't exist", HttpStatus.NOT_FOUND);
    }
}
