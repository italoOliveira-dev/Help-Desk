package br.com.estudo.microservice.orderservice.controllers.exceptions;

import models.exceptions.GenericFeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(GenericFeignException.class)
    public ResponseEntity<Map> handleGenericFeignException(final GenericFeignException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getError());
    }
}
