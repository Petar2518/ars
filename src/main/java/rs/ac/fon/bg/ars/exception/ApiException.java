package rs.ac.fon.bg.ars.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ApiException extends RuntimeException {
    private HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public ApiException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus=httpStatus;
    }
}
