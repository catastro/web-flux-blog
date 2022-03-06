package com.bootcamp.reto3.blog.handler;

import com.bootcamp.reto3.blog.entity.Author;
import com.bootcamp.reto3.blog.entity.Post;
import com.bootcamp.reto3.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class PostHandler {

    @Autowired
    PostService postService;

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Post.class)
                .flatMap(post -> postService.save(post))
                .flatMap(post -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(post), Post.class));
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(postService.findAll(), Post.class);
    }
}
