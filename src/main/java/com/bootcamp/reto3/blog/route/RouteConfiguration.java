package com.bootcamp.reto3.blog.route;

import com.bootcamp.reto3.blog.entity.Author;
import com.bootcamp.reto3.blog.exception.GlobalExceptionHandler;
import com.bootcamp.reto3.blog.handler.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jdk.jfr.ContentType;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RouterOperations(
            {
                    @RouterOperation(
                            path = "/authors",
                            produces = {MediaType.APPLICATION_JSON_VALUE},
                            method = RequestMethod.GET,
                            beanClass = AuthorHandler.class,
                            beanMethod = "findAll",
                            operation = @Operation(
                                    summary = "Listar Autores",
                                    description = "Listar todos los autores registrados",
                                    operationId = "findAll",
                                    responses = {
                                            @ApiResponse(responseCode = "200",
                                            description = "operacion exitosa",
                                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Author.class)))),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "No se encontro elementos",
                                                    content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.HttpError.class))
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(path = "/authors/{id}",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    beanClass = AuthorHandler.class,
                    beanMethod = "findById",
                    operation = @Operation(
                            operationId = "findById",
                            responses = {
                                    @ApiResponse(responseCode = "200",
                                    description = "operacion exitosa",
                                    content = @Content(schema = @Schema(implementation = Author.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Author"),
                                    @ApiResponse(responseCode = "404", description = "Author not found")
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id")
                            }
                    )),
                    @RouterOperation(path = "/authors",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = AuthorHandler.class,
                    beanMethod = "save",
                    operation = @Operation(
                            operationId = "save",
                            responses = {
                                    @ApiResponse(responseCode = "200",
                                    description = "Operacion exitosa",
                                    content = @Content(schema = @Schema(implementation = Author.class)))
                            }
                    )),
                    @RouterOperation(path = "/subscribers",
                            produces = {MediaType.APPLICATION_JSON_VALUE},
                            method = RequestMethod.DELETE,
                            beanClass = AuthorHandler.class,
                            beanMethod = "delete",
                            operation = @Operation(
                                    operationId = "delete",
                                    responses = {
                                            @ApiResponse(responseCode = "200", description = "successful operation",
                                                    content = @Content(schema = @Schema(implementation = Author.class)))},
                                    parameters = {},
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Author.class))))
                    )
            }
    )
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
