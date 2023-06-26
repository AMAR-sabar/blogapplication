package com.blogapp.services.impl;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exception.ResourceNotFoundException;
import com.blogapp.payload.PostDto;
import com.blogapp.payload.PostResponse;
import com.blogapp.repository.CategoryRepo;
import com.blogapp.repository.PostRepo;
import com.blogapp.repository.UserRepo;
import com.blogapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
//create post
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId)
    {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," User Id",userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category"," Category Id",categoryId));
        Post post = modelMapper.map(postDto, Post.class);
        post.setPostId(userId);
        post.setImageName("default.png");
        post.setAddDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }
//Update Post
    @Override
    public PostDto updatePost(PostDto postDto, Integer postId)
    {
       Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post Id",postId));
       post.setTitle(postDto.getTitle());
       post.setContent(postDto.getContent());
       post.setImageName(postDto.getImageName());
       Post updatedPost = postRepo.save(post);
       return modelMapper.map(updatedPost,PostDto.class);
    }
//Get all Posts
    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy,String sortDir)
    {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable  p = PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost = postRepo.findAll(p);
        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDtos=allPosts.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContents(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElement(pagePost.getTotalElements());
        postResponse.setTotalPage(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

         return postResponse;
    }
//Get post by Id
    @Override
    public PostDto getPostById(Integer postId)
    {
        Post post= postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post Id",postId));
        return modelMapper.map(post, PostDto.class);
    }
//Delete post
    @Override
    public void deletePost(Integer postId)
    {
         Post deletePost = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post Id", postId));
         postRepo.delete(deletePost);
    }
    //Get Post by Category
    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId)
    {
        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","category id", categoryId));
        List<Post> posts = postRepo.findByCategory(cat);
        List<PostDto> postDtos = posts.stream()
                .map((p)->modelMapper.map(p,PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }
//get Post by User
    @Override
    public List<PostDto> getPostsByUser(Integer userId)
    {
        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","User Id",userId));
        List<Post> posts = postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream()
                .map((p)->modelMapper.map(p,PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }
//Search Post
    @Override
    public List<PostDto> searchPost(String key)
    {
        List<Post> posts = postRepo.findByTitleContainingOrContentContaining(key, key);
        List<PostDto> postDtos = posts.stream()
                .map(p -> modelMapper.map(p, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }
}
