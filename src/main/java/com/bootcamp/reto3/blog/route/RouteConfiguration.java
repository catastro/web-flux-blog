package com.bootcamp.reto3.blog.route;

import com.bootcamp.reto3.blog.handler.*;
import jdk.jfr.ContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {
        return RouterFunctions.nest(path("/users"),
                RouterFunctions
                        .route(POST("").and(contentType(MediaType.APPLICATION_JSON)), userHandler::save)
                        .andRoute(POST("/login").and(contentType(MediaType.APPLICATION_JSON)), userHandler::login)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> blogRoutes(BlogHandler blogHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/blogs"),
                RouterFunctions
                        .route(GET(""), blogHandler::findAll)
                        .andRoute(POST("").and(accept(APPLICATION_JSON)),blogHandler::save)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> authorRoutes(AuthorHandler authorHandler){
        return RouterFunctions.nest(RequestPredicates.path("/authors"),
                RouterFunctions
                        .route(GET(""), authorHandler::findAll)
                        .andRoute(GET("/{id}"), authorHandler::findById)
                        .andRoute(POST("").and(accept(APPLICATION_JSON)),authorHandler::save)
                        .andRoute(DELETE("/{id}").and(accept(APPLICATION_JSON)),authorHandler::delete)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> postRoutes(PostHandler postHandler){
        return RouterFunctions.nest(RequestPredicates.path("/posts"),
                RouterFunctions
                        .route(GET(""), postHandler::findAll)
                        .andRoute(POST("").and(accept(APPLICATION_JSON)),postHandler::save)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> commentRoutes(CommentHandler commentHandler){
        return RouterFunctions.nest(RequestPredicates.path("/comments"),
                RouterFunctions
                        .route(GET(""), commentHandler::findAll)
                        .andRoute(POST("").and(accept(APPLICATION_JSON)),commentHandler::save)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> reactionRoutes(ReactionHandler reactionHandler){
        return RouterFunctions.nest(RequestPredicates.path("/reactions"),
                RouterFunctions
                        .route(POST("").and(accept(APPLICATION_JSON)), reactionHandler::save)
                        .andRoute(DELETE("/{id}"), reactionHandler::delete)
        );
    }
}
