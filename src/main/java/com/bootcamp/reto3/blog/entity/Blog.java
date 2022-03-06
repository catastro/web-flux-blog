package com.bootcamp.reto3.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "blogs")
public class Blog {

        @Id
        private String id;
        @Field(name = "name")
        private String name;
        private String authorId;
        private String url;
        private String status;
}
