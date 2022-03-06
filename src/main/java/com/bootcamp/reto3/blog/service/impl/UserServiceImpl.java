package com.bootcamp.reto3.blog.service.impl;

import com.bootcamp.reto3.blog.entity.Author;
import com.bootcamp.reto3.blog.entity.User;
import com.bootcamp.reto3.blog.exception.BadRequestException;
import com.bootcamp.reto3.blog.exception.NotFoundException;
import com.bootcamp.reto3.blog.repository.AuthorRepository;
import com.bootcamp.reto3.blog.repository.UserRepository;
import com.bootcamp.reto3.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Mono<User> save(User user) {
        return authorRepository.findById(user.getAuthorId())
                .switchIfEmpty(Mono.error(new NotFoundException("Autor no existe")))
                .flatMap(author -> userRepository.findByAuthorId(user.getAuthorId()).count())
                .flatMap(count -> count > 0
                        ? Mono.error(new BadRequestException("Usuario ya existe para autor ingresado"))
                        : userRepository.save(user));
    }

    @Override
    public Mono<User> login(String user, String password) {
        return userRepository.findByLoginAndPassword(user, password);
    }
}
