package com.example.SpringSecurityDemo.error;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ApiError {

    private LocalDateTime time;
    private String error;
    private HttpStatus statusCode;

    public  ApiError(){
        this.time= LocalDateTime.now();
    }

    public  ApiError(String error , HttpStatus statusCode){
        this();
        this.error = error;
        this.statusCode = statusCode;

    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
