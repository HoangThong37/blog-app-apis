package com.codewithme.blog.services.impl;

import com.codewithme.blog.entities.CategoryEntity;
import com.codewithme.blog.entities.PostEntity;
import com.codewithme.blog.entities.UserEntity;
import com.codewithme.blog.exceptions.ResourceNotFoundException;
import com.codewithme.blog.payloads.PostDTO;
import com.codewithme.blog.payloads.PostResponse;
import com.codewithme.blog.repositories.CategoryRepository;
import com.codewithme.blog.repositories.PostRepository;
import com.codewithme.blog.repositories.UserRepository;
import com.codewithme.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(" user ", " userId ", userId));

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(" category ", " categoryId ", categoryId));

        // convert data dto -> entity
        PostEntity postEntity = modelMapper.map(postDTO, PostEntity.class);
        //postEntity.setTitle(postDTO.getTitle());
        postEntity.setImageName("default.png");
        postEntity.setAddDate(new Date());
        postEntity.setUser(userEntity);
        postEntity.setCategory(categoryEntity);
        // saved
        PostEntity savedPost = postRepository.save(postEntity);
        PostDTO postDto = modelMapper.map(savedPost, PostDTO.class); // convert data entity -> dto
        return postDto;
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer id) {
        PostEntity postId = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" post ", " postId ", id));

        postId.setTitle(postDTO.getTitle());
        postId.setContent(postDTO.getContent());
        postId.setImageName(postDTO.getImageName());
      // postId.setAddDate(postDTO.getAddDate());
        PostEntity save = postRepository.save(postId);
        return modelMapper.map(save, PostDTO.class);
    }

    @Override
    public void deletePost(Integer id) {
        PostEntity deletePost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" post ", " postId Deleted ", id));
        postRepository.delete(deletePost);
    }

    @Override
    public PostDTO getPostById(Integer id) {
        PostEntity postById =  postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" post ", " post Id ", id));
        PostDTO postDTO = modelMapper.map(postById, PostDTO.class);
        return postDTO;
    }

    @Override
    public PostResponse getAll(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        Page<PostEntity> pagePost =  postRepository.findAll(p);
        List<PostEntity> postDtoAll = pagePost.getContent();

       // List<PostEntity> allPosts = postRepository.findAll();
        List<PostDTO> postDtos =  postDtoAll.stream().map(post -> modelMapper.map(post, PostDTO.class))
                                                     .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());

        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public List<PostDTO> getPostByCategory(Integer categoryId) {
        // categoy : thể loại
        CategoryEntity getCateById = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(" categoy ", " categoy Id ", categoryId));
         List<PostEntity> posts = postRepository.findByCategory(getCateById);
         List<PostDTO> postDTO = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        return postDTO;
    }

    @Override
    public List<PostDTO> getPostByUser(Integer userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(" user ", " user Id ", userId));
        List<PostEntity> posts = postRepository.findByUser(userEntity);
        List<PostDTO> postDTO = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        return postDTO;
    }

    @Override
    public List<PostDTO> searchPost(String keyword) {
        return null;
    }
}
