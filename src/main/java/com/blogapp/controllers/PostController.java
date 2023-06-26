package com.blogapp.controllers;

import com.blogapp.config.ConstantValue;
import com.blogapp.entities.Post;
import com.blogapp.payload.FileResponse;
import com.blogapp.payload.PostDto;
import com.blogapp.payload.PostResponse;
import com.blogapp.services.FileService;
import com.blogapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.List;

@Controller
@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //create post
    @PostMapping("/users/{userId}/categories/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId
            )
    {
        PostDto createdPost = this.postService.createPost(postDto, userId,categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }


    //update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId)
    {
        PostDto updatedPost = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Integer postId)
    {
        postService.deletePost(postId);
        return new ResponseEntity<>("Post Deleted Successfully", HttpStatus.OK);
    }


    //get all post
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestBody PostDto postDto,
            @RequestParam(value = "pageNumber",defaultValue = ConstantValue.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = ConstantValue.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = ConstantValue.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = ConstantValue.SORT_DIR,required = false) String sortDir
            )
    {
          PostResponse postResponse =  postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
            return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //get post by id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
    {
        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    //get post by user
    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId)
    {
        List<PostDto> getPosts = postService.getPostsByUser(userId);
        return new ResponseEntity<>(getPosts,HttpStatus.OK);
    }


    //get post by category
    @GetMapping("/categories/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId)
    {
        List<PostDto> getPosts = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(getPosts,HttpStatus.OK);
    }
//Search Post
    @GetMapping("/posts/search/{key}")
    public ResponseEntity<List<PostDto>> searchPost(@PathVariable String key) {
        List<PostDto> postDtos = postService.searchPost(key);
        return ResponseEntity.ok(postDtos);
    }

    //post image upload
    @PostMapping("post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId
    ) throws IOException
    {
            PostDto postDto = this.postService.getPostById(postId);
            String fileName = this.fileService.uploadImage(path,image);

            postDto.setImageName(fileName);
            PostDto updatePost = this.postService.updatePost(postDto,postId);
            return new ResponseEntity<>(updatePost,HttpStatus.OK);
    }
    //download image
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public  void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = fileService.getImage(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
