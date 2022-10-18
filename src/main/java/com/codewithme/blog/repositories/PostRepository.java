package com.codewithme.blog.repositories;

import com.codewithme.blog.entities.CategoryEntity;
import com.codewithme.blog.entities.PostEntity;
import com.codewithme.blog.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findByUser(UserEntity userEntity); // lấy user
    List<PostEntity> findByCategory(CategoryEntity categoryEntity);
    List<PostEntity> findByTitleContaining(String title);
}
