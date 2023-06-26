package com.blogapp.services.impl;

import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import com.blogapp.exception.ResourceNotFoundException;
import com.blogapp.payload.CommentDto;
import com.blogapp.repository.CommentRepo;
import com.blogapp.repository.PostRepo;
import com.blogapp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post","Post Id",postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);//comment to updated need changes
        Comment savedComment = commentRepo.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
       Comment comment = commentRepo.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment","CommentId",commentId));
       commentRepo.delete(comment);
    }
}
