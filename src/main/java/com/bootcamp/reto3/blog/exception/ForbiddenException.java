package com.bootcamp.reto3.blog.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForbiddenException extends RuntimeException {

    private HttpStatus status  = HttpStatus.FORBIDDEN;
    private String message;

    public ForbiddenException(String message){
        this.message = message;
    }


}