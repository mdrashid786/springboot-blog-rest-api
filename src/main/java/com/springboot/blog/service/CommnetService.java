package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;

import java.util.List;

public interface CommnetService {

    public CommentDto createComment(long postId, CommentDto commentDto);

    public List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(Long postId,Long commentId);

    CommentDto updateCommentByPostId(Long postId, Long commentId, CommentDto commentDto);

    void deleteCommentById(Long postId, Long commentId);

}
