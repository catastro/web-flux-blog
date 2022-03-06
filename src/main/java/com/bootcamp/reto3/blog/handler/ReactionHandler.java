package com.bootcamp.reto3.blog.handler;

import com.bootcamp.reto3.blog.entity.Reaction;
import com.bootcamp.reto3.blog.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ReactionHandler {

    @Autowired
    ReactionService reactionService;

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Reaction.class)
                .flatMap(reaction -> reactionService.save(reaction))
                .flatMap(reaction -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(reaction), Reaction.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String reactionId = request.pathVariable("id");

        return reactionService.delete(reactionId)
                .then(ServerResponse.ok().build());
    }
}
