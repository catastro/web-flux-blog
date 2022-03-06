package com.bootcamp.reto3.blog.service;

import com.bootcamp.reto3.blog.entity.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentService {

    Mono<Comment> save(Comment comment);

    Flux<Comment> findAll();
}
