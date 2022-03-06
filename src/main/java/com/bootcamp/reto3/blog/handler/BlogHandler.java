package com.bootcamp.reto3.blog.handler;

import com.bootcamp.reto3.blog.entity.Blog;
import com.bootcamp.reto3.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class BlogHandler {

    @Autowired
    BlogService blogService;

    public Mono<ServerResponse> save(ServerRequest request){
        return request.bodyToMono(Blog.class)
                .flatMap(blog -> blogService.save(blog))
                .flatMap(blog -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(blog), Blog.class));

    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(blogService.findAll(), Blog.class);
    }


}
