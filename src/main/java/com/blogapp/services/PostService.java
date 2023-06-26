package com.blogapp.services;

import com.blogapp.entities.Post;
import com.blogapp.payload.PostDto;
import com.blogapp.payload.PostResponse;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface PostService {
    //Create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    //update
    PostDto updatePost(PostDto postDto, Integer postId);
    //get
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
    //get post by id
    PostDto getPostById(Integer postId);

    //delete post
    void deletePost(Integer postId);

    //get post by category
    List<PostDto> getPostsByCategory(Integer categoryId);

        //get posts by user
    List<PostDto>  getPostsByUser(Integer userId);
    //search post
    List<PostDto> searchPost(String key);


}
