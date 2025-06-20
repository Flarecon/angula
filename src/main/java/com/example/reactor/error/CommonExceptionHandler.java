package com.example.reactor.error;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomExceptions(Exception e){
        return new ResponseEntity<>("Custom Error at " + LocalDateTime.now() + " says\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception e){
        return new ResponseEntity<>("error occured at " + LocalDateTime.now() + " says\n" + e.getMessage(), HttpStatus.NOT_FOUND);
    }
}