package com.bootcamp.reto3.blog.service.impl;

import com.bootcamp.reto3.blog.entity.Author;
import com.bootcamp.reto3.blog.entity.Blog;
import com.bootcamp.reto3.blog.entity.Post;
import com.bootcamp.reto3.blog.exception.NotFoundException;
import com.bootcamp.reto3.blog.repository.*;
import com.bootcamp.reto3.blog.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @Override
    public Mono<Author> findById(String id) {
        return this.authorRepository.findById(id);
    }

    @Override
    public Flux<Author> findAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public Mono<Author> save(Author author) {
        return this.authorRepository.save(author);
    }

    @Override
    public Flux<String> delete(String id) {
        AtomicReference<Author> atAuthor = new AtomicReference<>();
        AtomicReference<List<Blog>> atBlogs = new AtomicReference<>();
        AtomicReference<List<Post>> atPosts = new AtomicReference<>();


        return authorRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Author no existe")))
                .flatMap(author -> {
                    atAuthor.set(author);
                    return blogRepository.findByAuthorId(author.getId()).collectList();
                }).flatMap(blogs -> {
                    atBlogs.set(blogs);
                    return Mono.just(blogs);
                }).flatMapIterable(x -> x)
                .flatMap(blog -> postRepository.findByBlogId(blog.getId()).collectList())
                .flatMap(posts -> {
                    List<Post> listPost = atPosts.get();
                    if(!CollectionUtils.isEmpty(listPost)) {
                        listPost.addAll(posts);
                        atPosts.set(listPost);
                    }else {
                        atPosts.set(posts);
                    }
                    return Mono.just(posts);
                }).flatMapIterable(x -> x)
                .flatMap(post ->deleteCommentReact(post).thenReturn(post.getId()))
                .doOnComplete(() -> this.deleteCascade(atPosts.get(), atBlogs.get(), atAuthor.get()));
    }

    private Mono<Void> deleteCommentReact(Post post) {
        return commentRepository.findByPostId(post.getId()).collectList()
                .flatMap(comments -> commentRepository.deleteAll(comments).thenReturn(comments))
                .flatMap(comments -> reactionRepository.findByPostId(post.getId()).collectList())
                .flatMap(reactions -> reactionRepository.deleteAll(reactions));
    }

    private void deleteCascade(List<Post> posts, List<Blog> blogs, Author author) {
        postRepository.deleteAll(posts).thenReturn(posts)
                .flatMap(response -> blogRepository.deleteAll(blogs).thenReturn(blogs))
                .flatMap(response -> authorRepository.delete(author)).subscribe();
    }
}
