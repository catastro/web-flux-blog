package com.bootcamp.reto3.blog.service.impl;

import com.bootcamp.reto3.blog.entity.Blog;
import com.bootcamp.reto3.blog.exception.BadRequestException;
import com.bootcamp.reto3.blog.exception.ForbiddenException;
import com.bootcamp.reto3.blog.exception.NotFoundException;
import com.bootcamp.reto3.blog.repository.AuthorRepository;
import com.bootcamp.reto3.blog.repository.BlogRepository;
import com.bootcamp.reto3.blog.service.BlogService;
import com.bootcamp.reto3.blog.util.Constant;
import com.bootcamp.reto3.blog.util.UtilDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Mono<Blog> save(Blog blog) {
        blog.setStatus(Constant.BLOG_ACTIVE);
        return authorRepository.findById(blog.getAuthorId())
                .switchIfEmpty(Mono.error(new NotFoundException("Autor no existe")))
                .filter(author -> (UtilDate.getDifferenceDays(author.getBirthDate(), new Date()) > 18))
                .switchIfEmpty(Mono.error(new BadRequestException("Solo pueden tener blogs los autores mayores de 18 años")))
                .flatMap(author -> blogRepository.findByAuthorId(blog.getAuthorId()).count())
                .flatMap(count -> count >=3 ? Mono.error(new ForbiddenException("Autor puede tener maximo 3 blogs"))
                        : blogRepository.save(blog));
    }

    @Override
    public Flux<Blog> findAll() {
        return blogRepository.findAll();
    }
}
