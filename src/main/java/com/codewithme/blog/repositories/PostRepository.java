package com.codewithme.blog.repositories;

import com.codewithme.blog.entities.CategoryEntity;
import com.codewithme.blog.entities.PostEntity;
import com.codewithme.blog.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findByUser(UserEntity userEntity); // lấy user
    List<PostEntity> findByCategory(CategoryEntity categoryEntity);
   //  List<PostEntity> findByTitleContaining(String title);

    @Query("select p from PostEntity p where p.title like :key")
    List<PostEntity> searchByTitle(@Param("key") String title);
}
