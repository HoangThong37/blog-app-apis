package com.codewithme.blog.services.impl;

import com.codewithme.blog.entities.CommentEntity;
import com.codewithme.blog.entities.PostEntity;
import com.codewithme.blog.exceptions.ResourceNotFoundException;
import com.codewithme.blog.payloads.CommentDTO;
import com.codewithme.blog.payloads.PostDTO;
import com.codewithme.blog.repositories.CommentRepository;
import com.codewithme.blog.repositories.PostRepository;
import com.codewithme.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
        PostEntity postEntityId = postRepository.findById(postId)
                 .orElseThrow(() -> new ResourceNotFoundException(" post ", " postId", postId));
        CommentEntity commentEntity = modelMapper.map(commentDTO, CommentEntity.class);
        commentEntity.setPost(postEntityId);
        //commentEntity.setContent(postEntityId);

        CommentEntity savedComment = commentRepository.save(commentEntity);
        return modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(" comment ", " commentId", commentId));
        commentRepository.delete(comment);
    }
}
