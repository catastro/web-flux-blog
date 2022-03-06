package com.bootcamp.reto3.blog.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UnauthorizedException extends RuntimeException{
    private HttpStatus status = HttpStatus.UNAUTHORIZED;
    private String message;

    public UnauthorizedException(String message) {
        this.message = message;
    }
}
