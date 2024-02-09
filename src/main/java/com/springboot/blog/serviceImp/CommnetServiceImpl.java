package com.springboot.blog.serviceImp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommnetService;

@Service
public class CommnetServiceImpl implements CommnetService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    // Save New Comment Entry
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment=mapToEntity(commentDto);
        Post post=postRepository.findById(postId).orElseThrow(()-> new
                ResourceNotFoundException("post","id", postId));

        comment.setPost(post);
        Comment newComment= commentRepository.save(comment);

        return mapToDto(newComment);
    }

    // Get All Comments From PostId
    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

        List<Comment> comments=commentRepository.findByPostId(postId);

        return comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());
    }

    // Get Comment By PostId and CommentId
    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post","id", postId));

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->
               new ResourceNotFoundException("Comment","id",commentId));


        if (comment.getPost().getId()!=(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post:");

        }
        return mapToDto(comment);
    }

    // Update Comment
    @Override
    public CommentDto updateCommentByPostId(Long postId, Long commentId, CommentDto commentDto) {
        Post post=postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post","id", postId));

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment","id", commentId));
        if (comment.getPost().getId()!=post.getId()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post.");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updateComment = commentRepository.save(comment);
        return mapToDto(updateComment);
    }

    // Delete Comment
    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment","id",commentId));
        commentRepository.delete(comment);
    }

    private CommentDto mapToDto (Comment comment) {
        CommentDto commentDto= new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment= new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
