package com.codewithme.blog.repositories;

import com.codewithme.blog.entities.CommentEntity;
import com.codewithme.blog.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

}
