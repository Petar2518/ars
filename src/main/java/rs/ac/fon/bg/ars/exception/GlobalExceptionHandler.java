package rs.ac.fon.bg.ars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(e,request);
        return new ResponseEntity<>("Whoops! Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDetails> handleApiException(ApiException e, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(e, request);
        return new ResponseEntity<>(errorDetails, e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValid(MethodArgumentNotValidException e, WebRequest request){
        Map<String,String> errors = new HashMap<>();
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
        for(ObjectError error: objectErrors){
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName,message);
        }
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
