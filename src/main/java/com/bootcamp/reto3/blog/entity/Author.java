package com.bootcamp.reto3.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(value="authors")
public class Author {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private Date birthDate;

}
