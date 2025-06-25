package com.example.reactor.error;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomExceptions(Exception e){
        return new ResponseEntity<>("Custom Error at " + LocalDateTime.now() + " says\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<?> handleRoleAuthDeniedExceptions(Exception e){
        return new ResponseEntity<>("Error at " + LocalDateTime.now() + " says" + "\n" +
        "message:" + e.getMessage() + "\n" +
        "cause:" + "you are not allowed to access the requested resource"
        , HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredsExceptions(Exception e){
        return new ResponseEntity<>("Error at " + LocalDateTime.now() + " says" + "\n" +
        "message:" + e.getMessage() + "\n" +
        "cause:" + "invalid username or password"
        , HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception e){
        return new ResponseEntity<>(
            "error occured at " + LocalDateTime.now() + " says" + "\n" + 
            "message : " + e.getMessage() + "\n" +
            "cause : " + e.getCause() + "\n" + 
            "stacktrace : " + e.getStackTrace() + "\n" + 
            "class : " + e.getClass() + "\n", HttpStatus.NOT_FOUND);
    }
}