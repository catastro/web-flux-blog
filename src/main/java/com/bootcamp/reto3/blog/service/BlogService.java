package com.bootcamp.reto3.blog.service;

import com.bootcamp.reto3.blog.entity.Blog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BlogService {

    Mono<Blog> save(Blog blog);
    Flux<Blog> findAll();
}
