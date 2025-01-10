package com.librarymanagement.librarymanagement.controllers;

import com.librarymanagement.librarymanagement.dto.CategoryDto;
import com.librarymanagement.librarymanagement.dto.PageableResponse;
import com.librarymanagement.librarymanagement.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/api/admin/category/add")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){

        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return ResponseEntity.ok(savedCategory);
    }

    @PostMapping("/api/admin/category/delete/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long id) {

        CategoryDto deletedCategory = categoryService.deleteCategory(id);
        return ResponseEntity.ok(deletedCategory);
    }

    @GetMapping("/categories")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(categoryService.findAllCategories(page, size));
    }
}
