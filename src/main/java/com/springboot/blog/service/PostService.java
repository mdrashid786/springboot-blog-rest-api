package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    List<Post> getAllPost();

    // Pagination with page no and page size
    PostResponse getAllPostsWithPageNoAndPageSize(int pageNo, int pageSize);

    // Pagination with page no and page size and sort by
    PostResponse getAllPostsWithPageNoAndPageSizeAndSortBy(int pageNo, int pageSize, String sortBy);

    // Pagination with page no and page size and sort by and Ascending and Decending
    PostResponse getAllPostsWithPageNoAndPageSizeAndSortByAnsAscendingandDecending(
            int pageNo, int pageSize, String sortBy, String sortDir);


    Post deletePost(Long id);
    PostDto createPost(PostDto postDto);

    PostDto getPostById(Long id);
    Post getPostFromId(Long id);

    PostDto updatePostById(PostDto postDto, Long id);

    void deletePostById(Long id);
}
