package com.bootcamp.reto3.blog.repository;

import com.bootcamp.reto3.blog.entity.Author;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
