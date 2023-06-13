package com.bosch.library.library.controllers.errors;

import com.bosch.library.library.controllers.errors.exceptions.ApiUnavailableException;
import com.bosch.library.library.controllers.errors.exceptions.ElementNotFoundException;
import com.bosch.library.library.controllers.errors.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {
    Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(ApiUnavailableException.class)
    public ResponseEntity<Object> handleApiNotAvailableException(final ApiUnavailableException e) {
        this.logger.error("Generating a fake book failed and ApiUnavailableException occurred with the following message: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<Object> handleApiNotAvailableException(final ElementNotFoundException e) {
        this.logger.error("Тhe attempted action failed and ElementNotFoundException occurred with the following message: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleApiNotAvailableException(final ValidationException e) {
        this.logger.error("Тhe attempted action failed and ValidationException occurred with the following message: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
