package com.codewithme.blog.services;

import com.codewithme.blog.payloads.CommentDTO;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO, Integer postId);
    void deleteComment(Integer commentId);

}
