package com.bootcamp.reto3.blog.service;

import com.bootcamp.reto3.blog.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> save(User user);

    Mono<User> login(String user, String password);

}
