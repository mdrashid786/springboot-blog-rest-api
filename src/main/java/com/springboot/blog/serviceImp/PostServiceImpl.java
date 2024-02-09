package com.springboot.blog.serviceImp;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPost() {
        List<Post> posts=postRepository.findAll();
        return posts;
    }

    // Pagination only page No and page Size;
    @Override
    public PostResponse getAllPostsWithPageNoAndPageSize(int pageNo, int pageSize){

        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<Post> posts=postRepository.findAll(pageable);
        List<Post> listOfPage=posts.getContent();
        //return listOfPage.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        List<PostDto> content= new ArrayList<>();
        for(Post p:listOfPage) {
            content.add(mapToDto(p));
        }
        PostResponse postResponse= new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    // Pagination with page No and page Size and sorting by
    @Override
    public PostResponse getAllPostsWithPageNoAndPageSizeAndSortBy(int pageNo, int pageSize, String sortBy){

        Pageable pageable= PageRequest.of(pageNo,pageSize,Sort.by(sortBy));
        Page<Post> posts=postRepository.findAll(pageable);
        List<Post> listOfPage=posts.getContent();
        //return listOfPage.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        List<PostDto> content= new ArrayList<>();
        for(Post p:listOfPage) {
            content.add(mapToDto(p));
        }
        PostResponse postResponse= new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    // Pagination with pageNo and pageSize and sorting by and Ascesnding and decending
    @Override
    public PostResponse getAllPostsWithPageNoAndPageSizeAndSortByAnsAscendingandDecending(
            int pageNo, int pageSize, String sortBy,String sortDir){

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        Page<Post> posts=postRepository.findAll(pageable);
        List<Post> listOfPage=posts.getContent();
        //return listOfPage.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        List<PostDto> content= new ArrayList<>();
        for(Post p:listOfPage) {
            content.add(mapToDto(p));
        }
        PostResponse postResponse= new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    // Dto to Post
//    @Override
//    public List<PostDto> getAllPost(){
//        List<Post> postList=postRepository.findAll();
//        return postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
//    }

    @Override
    public Post deletePost(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            postRepository.deleteById(id);
            return post; // Return the deleted post if needed
        } else {
            // Handle case where post with the given ID doesn't exist
            return null; // Or throw an exception or return an appropriate response
        }
    }



    @Override
    public PostDto createPost(PostDto postDto) {

        Post post= mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        PostDto postResponse = mapToDto(newPost);
        return  postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post= postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    public Post getPostFromId(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        return postOptional.orElse(null); // or handle the case when the post is not found
    }

    @Override
    public PostDto updatePostById(PostDto postDto, Long id) {
        Post post= postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post = postRepository.save(post);
        return mapToDto(post);
    }

    @Override
    public void deletePostById(Long id) {
        Post deletePost= postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(deletePost);
    }

    private PostDto mapToDto(Post newPost) {
        PostDto postResponse = new PostDto();
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());
        postResponse.setContent(newPost.getContent());
        return postResponse;
    }

        private Post mapToEntity(PostDto postDto) {
            Post post = new Post();
            post.setId(postDto.getId());
            post.setTitle(postDto.getTitle());
            post.setDescription(postDto.getDescription());
            post.setContent(postDto.getContent());
            return post;
        }
}
