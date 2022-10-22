package com.codewithme.blog.entities;

import com.codewithme.blog.payloads.CategoryDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post")
@NoArgsConstructor
@Getter
@Setter
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postId;
    @Column(name = "title", length = 100, nullable = false)
    private String title;
    @Column(name = "content", length = 10000)
    private String content;
    @Column(name = "imageName")
    private String imageName;
    @Column(name = "addDate")
    private Date addDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne
    // @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<CommentEntity> comments = new HashSet<>();

}
