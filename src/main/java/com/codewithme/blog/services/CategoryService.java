package com.codewithme.blog.services;

//import com.codewithme.blog.payloads.CategoryDTO;

import com.codewithme.blog.payloads.CategoryDTO;

import java.util.List;

public interface CategoryService {

    // create
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    // update
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);
    //delete
    void deleteCategory(Integer id);
    //getbyId
    CategoryDTO getCategoryById(Integer id);
    //getAll
    List<CategoryDTO> getAllCategorys();
}
