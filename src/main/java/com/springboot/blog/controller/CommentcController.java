package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommnetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentcController {

    @Autowired
    private CommnetService commnetService;

    // Create Comments by postId
    // http://localhost:8585/api/posts/357/comment
    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long postId,
                                                    @RequestBody CommentDto commentDto) {
        return  new ResponseEntity<>(commnetService.createComment(postId,commentDto),
                HttpStatus.CREATED);

    }

    // Get All Comment by postId
    // http://localhost:8585/api/posts/357/comment
    @GetMapping("/posts/{postId}/comment")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("postId") long postId) {
        List<CommentDto>allComments=commnetService.getCommentsByPostId(postId);

        return new ResponseEntity<>(allComments,HttpStatus.OK);
    }

    // Get Comment by postId and commentId
    // http://localhost:8585/api/posts/357/comment/2/
    @GetMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId,
                                                           @PathVariable("commentId") Long commentId) {
        CommentDto allComments=commnetService.getCommentById(postId,commentId);

        return new ResponseEntity<>(allComments,HttpStatus.OK);


    }

    // Update Comment by postId and commentId
    // http://localhost:8585/api/posts/357/comment/2/update
    @PutMapping("/posts/{postId}/comment/{commentId}/update")
    public ResponseEntity<CommentDto> updateCommentByPostId(@PathVariable("postId") Long postId,
                                                            @PathVariable("commentId") Long commentId,
                                                            @RequestBody CommentDto commentDto) {
        CommentDto updateComment=commnetService.updateCommentByPostId(postId,commentId, commentDto);

        return new ResponseEntity<>(updateComment,HttpStatus.OK);


    }

    // Delete Comment by commentId
    // http://localhost:8585/api/posts/357/delete/2
    @DeleteMapping("/posts/{postId}/delete/{commentId}")
    public ResponseEntity<String> deleteCommentFromId(@PathVariable("postId") Long postId,
                                                      @PathVariable("commentId") Long commentId){
        commnetService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>("Successfully Delete", HttpStatus.OK);
    }
}
