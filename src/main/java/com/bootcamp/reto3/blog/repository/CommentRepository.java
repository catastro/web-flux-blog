package com.bootcamp.reto3.blog.repository;

import com.bootcamp.reto3.blog.entity.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Flux<Comment> findByPostId(String postId);
}
