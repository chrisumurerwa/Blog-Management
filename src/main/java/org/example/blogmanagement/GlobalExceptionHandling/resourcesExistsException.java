package org.example.blogmanagement.GlobalExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class resourcesExistsException extends RuntimeException {
    public resourcesExistsException(String message) {
        super(message);
    }
}
