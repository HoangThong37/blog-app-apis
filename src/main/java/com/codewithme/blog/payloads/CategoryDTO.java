package com.codewithme.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
    private Integer categoryId;

    @NotBlank
    @Size(min = 4, message = "Min size of category title is 4")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10,  message = "Min size of category desc is 4")
    private String categoryDescription;
}
