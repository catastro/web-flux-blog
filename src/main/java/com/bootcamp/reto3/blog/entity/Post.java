package com.bootcamp.reto3.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(value="posts")
public class Post {
    @Id
    private String id;
    private String title;
    @NotNull
    @NotEmpty
//    @JsonFormat(pattern="yyyy-MM-dd", timezone="America/Lima")
    private Date date;
    private String status;
    @NotNull
    @NotEmpty
    private String content;
    @NotNull
    private String blogId;
}
