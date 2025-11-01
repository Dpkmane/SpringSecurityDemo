package com.example.SpringSecurityDemo.error;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.JMException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNameNotFoundException(UsernameNotFoundException exception){

        ApiError apiError = new ApiError("user not fount"+exception.getMessage(), HttpStatus.NOT_FOUND);
        return  new ResponseEntity<>(apiError , apiError.getStatusCode());
    }

    @ExceptionHandler(AuthenticationException.class)
    public  ResponseEntity<ApiError> authenticationException(AuthenticationException authenticationException){
        ApiError apiError = new ApiError("Authentication Failed "+authenticationException.getMessage(), HttpStatus.UNAUTHORIZED);
        return  new ResponseEntity<>(apiError , apiError.getStatusCode());
    }

    @ExceptionHandler(JwtException.class)
    public  ResponseEntity<ApiError> jwtException(JwtException jwtException){
        ApiError apiError = new ApiError("Invalid jwt Token "+jwtException.getMessage(), HttpStatus.UNAUTHORIZED);
        return  new ResponseEntity<>(apiError , apiError.getStatusCode());
    }
}
