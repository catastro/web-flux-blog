package com.bootcamp.reto3.blog.repository;

import com.bootcamp.reto3.blog.entity.Blog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BlogRepository extends ReactiveMongoRepository<Blog, String> {

    Flux<Blog> findByAuthorId(String authorId);
}
