package com.codewithme.blog.controllers;

import com.codewithme.blog.payloads.ApiResponse;
import com.codewithme.blog.payloads.CategoryDTO;
import com.codewithme.blog.payloads.UserDTO;
import com.codewithme.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // create
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }
    // update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable("categoryId") Integer id) {
        CategoryDTO update = categoryService.updateCategory(categoryDTO, id);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer id) {
          categoryService.deleteCategory(id);
          return new ResponseEntity<>(new ApiResponse("Category is deleted successfully !!", false), HttpStatus.OK);
    }

    //getbyid
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("categoryId") Integer IdCategories) {
        CategoryDTO getByIdCateg = categoryService.getCategoryById(IdCategories);
       // return ResponseEntity.ok(getByIdCateg);
        return new ResponseEntity<CategoryDTO>(getByIdCateg, HttpStatus.OK);
    }

    //getall
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        List<CategoryDTO> category = categoryService.getAllCategorys();
        return ResponseEntity.ok(category);
    }
}
