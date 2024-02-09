package com.springboot.blog.controller;

import com.springboot.blog.config.SecurityConfig;
import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.Users;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.UsersRepository;
import com.springboot.blog.service.PostService;
import com.springboot.blog.serviceImp.PostServiceImpl;
import com.springboot.blog.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostServiceImpl postServiceImpl;
    
	@Autowired
	private UsersRepository usersRepository;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    
    //@PreAuthorize("hasRole('ADMIN')") //This Mapping Annotation for only Admin 
    // create blog post Rest Api
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
            PostDto newPostDto=postService.createPost(postDto);
            System.out.println("I create 1 post ");
            return new ResponseEntity<>(newPostDto, HttpStatus.CREATED);
    }

    @GetMapping("/get/post")
    public ResponseEntity<List<Post>> getPost(@RequestBody Post post){
        List<Post> getAllPostList=postService.getAllPost();
        System.out.println("List Size : "+getAllPostList.size());
        return new ResponseEntity<>(getAllPostList,HttpStatus.CREATED);
    }


    // Pagination with pageNo and pageSize
    // http://localhost:8585/api/posts/p?pageNo=0&pageSize=10
    @GetMapping
    public ResponseEntity<PostResponse> getAllPostsWithPageNoAndPageSize(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "8", required = false) int pageSize
    ){
    	Optional<Users> users=usersRepository.findByUsername("rashid");
		System.out.println("password : "+users.get().getPassword());
		
        PostResponse postLists=postService.getAllPostsWithPageNoAndPageSize(pageNo, pageSize);
        System.out.println("I am only getting all data from post table : ");
        return new ResponseEntity<>(postLists, HttpStatus.OK);
    }

    // Pagination with pageNo and pageSize and sort by
    // http://localhost:8585/api/posts/p?pageNo=0&pageSize=10&sortBy=title
    @GetMapping("/p")
    public ResponseEntity<PostResponse> getAllPostsWithPageNoAndPageSizeAndSortBy(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "8", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
    ){
		
        PostResponse postLists=postService.getAllPostsWithPageNoAndPageSizeAndSortBy(pageNo, pageSize, sortBy);
        System.out.println("I am only getting all data from post table : ");
        return new ResponseEntity<>(postLists, HttpStatus.OK);
    }

    // Pagination with pageNo and pageSize and sort by
    // http://localhost:8585/api/posts/p?pageNo=0&pageSize=10&sortBy=title
    @GetMapping("/asc")
    public ResponseEntity<PostResponse> getAllPostsWithPageNoAndPageSizeAndSortBy(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        PostResponse postLists=postService.getAllPostsWithPageNoAndPageSizeAndSortByAnsAscendingandDecending(
                pageNo, pageSize, sortBy, sortDir);
        System.out.println("I am only getting all data from post table : ");
        return new ResponseEntity<>(postLists, HttpStatus.OK);
    }


//    // Dtp to Post
//    @GetMapping("/get/post")
//    public ResponseEntity<List<PostDto>> getPost(@RequestBody Post post){
//        List<PostDto> getAllPostList=postService.getAllPost();
//        System.out.println("I am only getting all data from post table ");
//        return new ResponseEntity<>(getAllPostList,HttpStatus.CREATED);
//    }

   // @PreAuthorize("hasRole('ADMIN')") //This Mapping Annotation for only Admin 
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {
        Post deletePost=postService.deletePost(id);
        if (deletePost!=null){
            System.out.println("I am going to delete ");
            return  ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
        }else {
            System.out.println("Not Found Exception");
            String errorMessage = "Post with ID " + id + " not found.";
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    //@PreAuthorize("hasRole('ADMIN')") //This Mapping Annotation for only Admin 
    @DeleteMapping("delete/by/{id}")
    public ResponseEntity<String> updatePostFromId( @PathVariable("id") Long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Successfully Delete", HttpStatus.OK);
    }

    // add key and value in header using post man key is x-API-version and value is 1
    @GetMapping(value = "/get/{id}", headers = "x-API-version=1")
    //@GetMapping(value = "/get/{id}", params = "version=1")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {
        PostDto postDto=postService.getPostById(id);
        return ResponseEntity.ok(postDto);

    }

    @GetMapping(value = "/{id}/post", headers = "x-API-version=2")
    //@GetMapping("/{id}/post")
    public ResponseEntity<Post> getPostFromId(@PathVariable("id") Long id) {
        Post post=postService.getPostFromId(id);
        return ResponseEntity.ok(post);
    }

   // @PreAuthorize("hasRole('ADMIN')") //This Mapping Annotation for only Admin 
    @PutMapping("update/{id}")
    public ResponseEntity<PostDto> updatePostFromId(@Valid @RequestBody PostDto postdto, @PathVariable("id") Long id){
        PostDto updatePost=postService.updatePostById(postdto,id);
        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }




}
