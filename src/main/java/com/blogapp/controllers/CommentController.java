package com.blogapp.controllers;

import com.blogapp.payload.ApiResponse;
import com.blogapp.payload.CommentDto;
import com.blogapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {
    @Autowired
    private CommentService commentService;

    //create comment
    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable Integer postId)
    {
       CommentDto comment =commentService.createComment(commentDto,postId);
       return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }
    //delete comment
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
    {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Deleted Successfully",true), HttpStatus.OK);
    }


}
