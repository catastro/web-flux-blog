package com.bootcamp.reto3.blog.service;

import com.bootcamp.reto3.blog.entity.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {
    Mono<Author> findById(String id);
    Flux<Author> findAll();
    Mono<Author> save(Author author);
    Flux<String> delete(String id);
}
