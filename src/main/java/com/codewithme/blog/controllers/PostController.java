package com.codewithme.blog.controllers;

import com.codewithme.blog.payloads.ApiResponse;
import com.codewithme.blog.payloads.PostDTO;
import com.codewithme.blog.payloads.PostResponse;
import com.codewithme.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    // create post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO,
                                                  @PathVariable Integer userId,
                                                  @PathVariable Integer categoryId) {
        PostDTO postDto = postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<PostDTO>(postDto, HttpStatus.CREATED);
    }

    // get post by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable Integer userId) {
       List<PostDTO> postByUser = postService.getPostByUser(userId);
       return new ResponseEntity<List<PostDTO>>(postByUser, HttpStatus.OK);
    }

    // get post by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDTO> postByCategory = postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDTO>>(postByCategory, HttpStatus.OK);
    }

    // get all post
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
             @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
             @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
             @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy
    ) {
        PostResponse postResponse = postService.getAll(pageNumber, pageSize, sortBy);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    // get post details by id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getPostId(@PathVariable Integer postId) {
        PostDTO postEntityId = postService.getPostById(postId);
        return new ResponseEntity<PostDTO>(postEntityId, HttpStatus.OK);
    }

    // delete post
    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable("postId") Integer id) {
        postService.deletePost(id);
        return new ApiResponse("Post is deleted successfully !!", true);
    }
    // update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO , @PathVariable("postId") Integer id) {
        PostDTO postUpdate = postService.updatePost(postDTO, id);
        return new ResponseEntity<PostDTO>(postUpdate, HttpStatus.OK);
    }
}
