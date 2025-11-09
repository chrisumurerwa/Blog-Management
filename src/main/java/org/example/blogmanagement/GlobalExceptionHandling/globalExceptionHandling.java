package org.example.blogmanagement.GlobalExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class globalExceptionHandling {
    @ExceptionHandler(value = resourcesNotFoundException.class)
    public ResponseEntity<String> resourceException(resourcesExistsException resource) {
        return new ResponseEntity<>(resource.getMessage(), HttpStatus.CONFLICT);
    }
}