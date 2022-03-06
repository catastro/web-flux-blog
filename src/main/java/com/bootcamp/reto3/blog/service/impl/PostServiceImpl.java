package com.bootcamp.reto3.blog.service.impl;

import com.bootcamp.reto3.blog.entity.Blog;
import com.bootcamp.reto3.blog.entity.Post;
import com.bootcamp.reto3.blog.exception.BadRequestException;
import com.bootcamp.reto3.blog.exception.NotFoundException;
import com.bootcamp.reto3.blog.repository.BlogRepository;
import com.bootcamp.reto3.blog.repository.PostRepository;
import com.bootcamp.reto3.blog.service.PostService;
import com.bootcamp.reto3.blog.util.Constant;
import com.bootcamp.reto3.blog.util.UtilDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    BlogRepository blogRepository;

    @Override
    public Mono<Post> save(Post post) {
        post.setDate(UtilDate.getNowDate());
        post.setStatus(Constant.POST_PUBLISHED);
        return blogRepository.findById(post.getBlogId())
                .switchIfEmpty(Mono.error(new NotFoundException("Blog no existe")))
                .filter(blog -> Constant.BLOG_ACTIVE.equals(blog.getStatus()))
                .switchIfEmpty(Mono.error(new BadRequestException("Solo se puede registrar posts en blogs en estado activo")))
                .flatMap(blog -> postRepository.findByBlogIdAndDate(blog.getId(),
                        UtilDate.getNowDate()).count())
                .flatMap(count -> count == 0 ? postRepository.save(post)
                        : Mono.error(new BadRequestException("Solo se puede publicar un post por día")));
    }

    @Override
    public Flux<Post> findAll() {
        return postRepository.findAll();
    }

}
