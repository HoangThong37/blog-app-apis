package com.codewithme.blog.payloads;

import com.codewithme.blog.entities.CategoryEntity;
import com.codewithme.blog.entities.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

     private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Date addDate;
    private CategoryDTO category;
    private UserDTO user;
}
