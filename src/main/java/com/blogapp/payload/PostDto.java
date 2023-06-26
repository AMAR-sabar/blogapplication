package com.blogapp.payload;

import com.blogapp.entities.Category;
import com.blogapp.entities.Comment;
import com.blogapp.entities.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.*;

@Getter
@Setter
public class PostDto {
    private Integer postId;  // Unique identifier for the blog post
    private String title;  // Title of the blog post
    private String content;  // Content or body of the blog post
    private String imageName;  // Name of the associated image
    private Date addedDate;  // Date when the blog post was added
    private UserDto user;  // User associated with the blog post (author)
    private CategoryDto category;  // Category associated with the blog post
    private List<CommentDto> comments = new ArrayList<>();  // List of comments for the blog post
}