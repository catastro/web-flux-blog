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
public class NotFoundException extends RuntimeException{
    private HttpStatus status = HttpStatus.NOT_FOUND;
    private String message;

    public NotFoundException(String message) {
        this.message = message;
    }
}
