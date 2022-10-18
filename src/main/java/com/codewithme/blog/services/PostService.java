package com.codewithme.blog.services;

import com.codewithme.blog.payloads.PostDTO;
import com.codewithme.blog.payloads.PostResponse;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface PostService {
    // create
    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

    // update
    PostDTO updatePost(PostDTO postDTO, Integer id);

    //delete
    void deletePost(Integer id);

    //get post by id
    PostDTO getPostById(Integer id);

    // get all
    PostResponse getAll(Integer pageNumber, Integer pageSize, String sortBy);

    // getAll post by category
    List<PostDTO> getPostByCategory(Integer categoryId);

    // get all posts by user
    List<PostDTO> getPostByUser(Integer userId);

    // search posts
    List<PostDTO> searchPost(String keyword);
}
