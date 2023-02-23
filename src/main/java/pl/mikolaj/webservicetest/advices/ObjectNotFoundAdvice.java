package pl.mikolaj.webservicetest.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.mikolaj.webservicetest.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ObjectNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(ObjectNotFoundException ex) {
        return ex.getMessage();
    }
}
