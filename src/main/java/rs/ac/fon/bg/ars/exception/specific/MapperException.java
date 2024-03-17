package rs.ac.fon.bg.ars.exception.specific;

import org.springframework.http.HttpStatus;
import rs.ac.fon.bg.ars.exception.ApiException;

public class MapperException extends ApiException {

    public MapperException(){
        super("Error while updating", HttpStatus.BAD_REQUEST);
    }
}
