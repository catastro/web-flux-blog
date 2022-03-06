package com.bootcamp.reto3.blog.repository;

import com.bootcamp.reto3.blog.entity.Post;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.Date;

public interface PostRepository extends ReactiveMongoRepository<Post, String> {

    Flux<Post> findByBlogIdAndDate(String blogId, Date date);

    Flux<Post> findByBlogId(String blogId);
}
