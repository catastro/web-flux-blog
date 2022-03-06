package com.bootcamp.reto3.blog.handler;

import com.bootcamp.reto3.blog.entity.User;
import com.bootcamp.reto3.blog.exception.UnauthorizedException;
import com.bootcamp.reto3.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    @Autowired
    UserService userService;

    public Mono<ServerResponse> save (ServerRequest request) {
        var userInput = request.bodyToMono(User.class);

        return userInput
                .flatMap(user -> userService.save(user))
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(user), User.class));
    }

    public Mono<ServerResponse> login (ServerRequest request) {
        var user = request.queryParam("user").get();
        var password = request.queryParam("password").get();

        return userService.login(user, password)
                .switchIfEmpty(Mono.error(new UnauthorizedException("Usuario o clave inválidos")))
                .flatMap(userResponse -> ServerResponse.ok().body(Mono.empty(), Void.class));
    }
}
