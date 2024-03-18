package rs.ac.fon.bg.ars.exception.specific;

import org.springframework.http.HttpStatus;
import rs.ac.fon.bg.ars.exception.ApiException;

public class ImageNotFoundException extends ApiException {
    public ImageNotFoundException(Long id){
        super("Image with id: " + id + " doesn't exist", HttpStatus.NOT_FOUND);
    }
}
