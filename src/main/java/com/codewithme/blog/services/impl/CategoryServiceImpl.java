package com.codewithme.blog.services.impl;

import com.codewithme.blog.entities.CategoryEntity;
import com.codewithme.blog.exceptions.ResourceNotFoundException;
import com.codewithme.blog.payloads.CategoryDTO;
import com.codewithme.blog.repositories.CategoryRepository;
import com.codewithme.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        // convert dto -> entity
        CategoryEntity categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
        CategoryEntity saveCategory = categoryRepository.save(categoryEntity);
        CategoryDTO categoryDto = modelMapper.map(saveCategory, CategoryDTO.class);
        return categoryDto;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                 .orElseThrow(() -> new ResourceNotFoundException(" Category ", " CategoryId", categoryId));
        // set lại giá trị
        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());

        CategoryEntity saveCate = categoryRepository.save(category);
        CategoryDTO categoryDto = modelMapper.map(saveCate, CategoryDTO.class);
        return categoryDto;
    }

    @Override
    public void deleteCategory(Integer id) {
        if (id != null) {
            CategoryEntity idDeleted = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", id));
            categoryRepository.delete(idDeleted);
        }
    }

    @Override
    public CategoryDTO getCategoryById(Integer id) {
        CategoryEntity categoryEntity = this.categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" category ", " categoryId ", id));
        CategoryDTO categoryDto = modelMapper.map(categoryEntity, CategoryDTO.class);
        return categoryDto;
    }
    // viết bằng java 7
//    @Override
//    public List<CategoryDTO> getAllCategorys() {
//        List<CategoryEntity> categoryEntity = categoryRepository.findAll();
//        List<CategoryDTO> result = new ArrayList<>();
//        for (CategoryEntity item : categoryEntity) {
//            CategoryDTO categoryDTO = modelMapper.map(item, CategoryDTO.class);
//            result.add(categoryDTO);
//        }
//        return result;
//    }

    // java 8
    // chưa test
    @Override
    public List<CategoryDTO> getAllCategorys() {
        List<CategoryEntity> categoryEntity = categoryRepository.findAll();
       // CategoryDTO categoryDTO = modelMapper.map(categoryEntity, CategoryDTO.class);
        List<CategoryDTO> result = categoryEntity.stream().map((cate) -> modelMapper.map(cate, CategoryDTO.class))
                                                 .collect(Collectors.toList());
        return result;
    }
}
