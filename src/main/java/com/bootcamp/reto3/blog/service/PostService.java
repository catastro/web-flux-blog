package com.bootcamp.reto3.blog.service;

import com.bootcamp.reto3.blog.entity.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {

    Mono<Post> save(Post post);
    Flux<Post> findAll();
}
