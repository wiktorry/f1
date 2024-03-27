package com.example.f1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class DriverExceptionHandler {
    @ExceptionHandler(value = {DriverNotFoundException.class})
    public ResponseEntity<Object> handleDriverException(RuntimeException exc){
        if(exc instanceof DriverNotFoundException){
            DriverExceptionResponse exceptionResponse = new DriverExceptionResponse(exc.getMessage(), HttpStatus.NOT_FOUND.value(), System.currentTimeMillis());
            return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
        }
        DriverExceptionResponse exception = new DriverExceptionResponse(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), System.currentTimeMillis());
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
