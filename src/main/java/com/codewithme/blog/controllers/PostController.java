package com.codewithme.blog.controllers;

import com.codewithme.blog.config.AppConstants;
import com.codewithme.blog.payloads.ApiResponse;
import com.codewithme.blog.payloads.PostDTO;
import com.codewithme.blog.payloads.PostResponse;
import com.codewithme.blog.services.FileService;
import com.codewithme.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

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
             @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PostResponse postResponse = postService.getAll(pageNumber, pageSize, sortBy, sortDir);
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

    // search post
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDTO>> searchPosts(@PathVariable("keywords") String keywords) {
        List<PostDTO> result = postService.searchPost(keywords);
        return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
    }
    // post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId) throws IOException {
        PostDTO postDTO = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);

        postDTO.setImageName(fileName);
        PostDTO updatePost = postService.updatePost(postDTO, postId);
        return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
    }

    // method to serve files
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws  IOException {
        InputStream resource =  fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
