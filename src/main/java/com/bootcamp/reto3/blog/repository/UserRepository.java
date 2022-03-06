package com.bootcamp.reto3.blog.repository;

import com.bootcamp.reto3.blog.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByLoginAndPassword(String user, String password);

    Flux<User> findByAuthorId(String authorId);
}
