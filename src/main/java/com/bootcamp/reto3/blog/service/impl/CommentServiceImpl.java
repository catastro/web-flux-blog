package com.bootcamp.reto3.blog.service.impl;

import com.bootcamp.reto3.blog.entity.Comment;
import com.bootcamp.reto3.blog.exception.BadRequestException;
import com.bootcamp.reto3.blog.exception.NotFoundException;
import com.bootcamp.reto3.blog.repository.CommentRepository;
import com.bootcamp.reto3.blog.repository.PostRepository;
import com.bootcamp.reto3.blog.repository.UserRepository;
import com.bootcamp.reto3.blog.service.CommentService;
import com.bootcamp.reto3.blog.util.Constant;
import com.bootcamp.reto3.blog.util.UtilDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Mono<Comment> save(Comment comment) {
        comment.setDate(UtilDate.getNowDate());
        return userRepository.findById(comment.getUserId())
                .switchIfEmpty(Mono.error(new NotFoundException("Usuario no existe")))
                .flatMap(user -> postRepository.findById(comment.getPostId()))
                .switchIfEmpty(Mono.error(new NotFoundException("Post no existe")))
                .filter(post -> Constant.POST_PUBLISHED.equals(post.getStatus()))
                .switchIfEmpty(Mono.error(new BadRequestException("Solo se pueden registrar comentarios en post en estado publicado")))
                .flatMap(post -> commentRepository.save(comment));
    }

    @Override
    public Flux<Comment> findAll() {
        return commentRepository.findAll();
    }
}
