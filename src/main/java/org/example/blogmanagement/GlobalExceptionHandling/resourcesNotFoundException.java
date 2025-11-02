package org.example.blogmanagement.GlobalExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class resourcesNotFoundException extends RuntimeException {
    public resourcesNotFoundException(String message) {
        super(message);
    }

}
