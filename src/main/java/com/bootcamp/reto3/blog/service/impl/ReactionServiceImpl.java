package com.bootcamp.reto3.blog.service.impl;

import com.bootcamp.reto3.blog.entity.Reaction;
import com.bootcamp.reto3.blog.exception.BadRequestException;
import com.bootcamp.reto3.blog.exception.NotFoundException;
import com.bootcamp.reto3.blog.repository.PostRepository;
import com.bootcamp.reto3.blog.repository.ReactionRepository;
import com.bootcamp.reto3.blog.repository.UserRepository;
import com.bootcamp.reto3.blog.service.ReactionService;
import com.bootcamp.reto3.blog.util.Constant;
import com.bootcamp.reto3.blog.util.UtilDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Mono<Reaction> save(Reaction reaction) {
        reaction.setDate(UtilDate.getNowDate());
        reaction.setType(Constant.REACTION_TYPE_LIKE);
        return userRepository.findById(reaction.getUserId())
                .switchIfEmpty(Mono.error(new NotFoundException("Usuario no existe")))
                .flatMap(user -> postRepository.findById(reaction.getPostId()))
                .switchIfEmpty(Mono.error(new NotFoundException("Post no existe")))
                .flatMap(post -> reactionRepository.findByUserIdAndPostId(reaction.getUserId(), reaction.getPostId()).count())
                .flatMap(count -> count == 0 ? reactionRepository.save(reaction)
                        : Mono.error(new BadRequestException("Un usuario solo puede tener una reacción para cada post")));
    }

    @Override
    public Mono<Void> delete(String id) {
        return reactionRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Reacion no encontrada")))
                .flatMap(reaction -> reactionRepository.delete(reaction));
    }
}
