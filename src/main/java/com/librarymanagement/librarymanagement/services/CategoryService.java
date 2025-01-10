package com.librarymanagement.librarymanagement.services;

import com.librarymanagement.librarymanagement.dto.CategoryDto;
import com.librarymanagement.librarymanagement.dto.PageableResponse;
import com.librarymanagement.librarymanagement.models.Category;
import com.librarymanagement.librarymanagement.repositories.CategoryRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepo categoryRepo, ModelMapper modelMapper) {
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
    }

   @Transactional
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto, Category.class);
        Category saved = categoryRepo.save(category);
        return modelMapper.map(saved, CategoryDto.class);

    }
    public CategoryDto deleteCategory(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow();
        categoryRepo.delete(category);
        return modelMapper.map(category, CategoryDto.class);
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public PageableResponse<CategoryDto> findAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepo.findAll(pageable);

        List<CategoryDto> categoryDtos = categoryPage.getContent().stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();

        PageableResponse<CategoryDto> response = new PageableResponse<>();
        response.setContent(categoryDtos);
        response.setPageNo(categoryPage.getNumber());
        response.setPageSize(categoryPage.getSize());
        response.setTotalElements(categoryPage.getTotalElements());
        response.setTotalPages(categoryPage.getTotalPages());
        response.setLast(categoryPage.isLast());

        return response;
    }

}
