package com.bootcamp.reto3.blog.handler;

import com.bootcamp.reto3.blog.entity.Author;
import com.bootcamp.reto3.blog.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class AuthorHandler {

    @Autowired
    private AuthorService authorService;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(authorService.findAll(), Author.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        var id = request.pathVariable("id");
//        return ServerResponse.ok()
//                .contentType(APPLICATION_JSON)
//                .body(authorService.findById(id), Author.class);

        return this.authorService.findById(id)
                .flatMap(a-> ServerResponse.ok().body(Mono.just(a), Author.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> save(ServerRequest request){

        var authorInput= request.bodyToMono(Author.class);

        return authorInput
                .flatMap(author-> this.authorService.save(author))
                .flatMap(a-> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(a), Author.class));
//                .switchIfEmpty(Mono.error(new AuthorExistsException("Author exists")));

    }

    public Mono<ServerResponse> delete(ServerRequest request){
        String authorId = request.pathVariable("id");

        return authorService.delete(authorId)
                .then(ServerResponse.ok().build());
    }

}
