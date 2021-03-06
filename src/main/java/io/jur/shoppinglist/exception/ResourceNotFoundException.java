package io.jur.shoppinglist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Class clazz, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", clazz, fieldName, fieldValue));
    }

}
