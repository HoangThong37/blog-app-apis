package com.codewithme.blog.controllers;

import com.codewithme.blog.payloads.ApiResponse;
import com.codewithme.blog.payloads.CommentDTO;
import com.codewithme.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // create
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Integer postId) {
        CommentDTO comment = commentService.createComment(commentDTO, postId);
        return new ResponseEntity<CommentDTO>(comment, HttpStatus.CREATED);
    }

    // delete
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deletedComment(@PathVariable("commentId") Integer commentId) {
         commentService.deleteComment(commentId);
         return new ResponseEntity<>(new ApiResponse("comment is deleted successfully !!", true), HttpStatus.OK);
    }
}
