package com.blogapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data  // Lombok annotation to generate getters, setters, `toString()`, `equals()`, and `hashCode()` methods for the fields.
@NoArgsConstructor  // Lombok annotation to generate a no-argument constructor.
@AllArgsConstructor  // Lombok annotation to generate a constructor with all fields.
@Entity  // JPA annotation to mark the class as a persistent entity.
@Table(name="comments")  // JPA annotation to specify the name of the database table to map the entity to.
public class Comment {
    @Id  // JPA annotation to indicate the primary key field of the entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // JPA annotation to specify the strategy for generating primary key values as an auto-incrementing identity column.
    private Integer commentId;  // Unique identifier for the comment

    private String content;  // Content of the comment

    @ManyToOne  // JPA annotation to establish a many-to-one relationship with the `Post` entity.
    private Post post;  // The blog post to which this comment belongs
}
