package com.bootcamp.reto3.blog.repository;

import com.bootcamp.reto3.blog.entity.Reaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ReactionRepository extends ReactiveMongoRepository<Reaction, String> {

    Flux<Reaction> findByUserIdAndPostId(String userId, String postId);

    Flux<Reaction> findByPostId(String postId);


}
