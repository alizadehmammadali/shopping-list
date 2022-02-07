package io.jur.shoppinglist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(Class clazz,String fieldName,String fieldValue) {
        super(String.format("%s is already exists with %s : '%s' .Please try another one", clazz, fieldName, fieldValue));
    }
}
