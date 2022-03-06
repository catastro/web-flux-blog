package com.bootcamp.reto3.blog.service;

import com.bootcamp.reto3.blog.entity.Reaction;
import reactor.core.publisher.Mono;

public interface ReactionService {

    Mono<Reaction> save(Reaction reaction);

    Mono<Void> delete(String id);
}
