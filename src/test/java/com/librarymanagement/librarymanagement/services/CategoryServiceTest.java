package com.librarymanagement.librarymanagement.services;

import com.librarymanagement.librarymanagement.dto.CategoryDto;
import com.librarymanagement.librarymanagement.dto.PageableResponse;
import com.librarymanagement.librarymanagement.models.Category;
import com.librarymanagement.librarymanagement.repositories.CategoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private CategoryRepo categoryRepo;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Test Category");
    }

    @Test
    void addCategory_Success() {
        when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepo.save(category)).thenReturn(category);
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        CategoryDto result = categoryService.addCategory(categoryDto);

        assertNotNull(result);
        assertEquals(categoryDto.getName(), result.getName());
        verify(categoryRepo).save(category);
    }

    @Test
    void deleteCategory_Success() {
        when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        CategoryDto result = categoryService.deleteCategory(1L);

        assertNotNull(result);
        verify(categoryRepo).delete(category);
    }

    @Test
    void deleteCategory_NotFound() {
        when(categoryRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> categoryService.deleteCategory(1L));
    }

    @Test
    void findAllCategories_Success() {

        List<Category> categories = List.of(category);
        Page<Category> categoryPage = new PageImpl<>(categories, PageRequest.of(0, 10), 1);

        when(categoryRepo.findAll(any(Pageable.class))).thenReturn(categoryPage);
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        PageableResponse<CategoryDto> response = categoryService.findAllCategories(0, 10);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals(0, response.getPageNo());
        assertEquals(10, response.getPageSize());
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
        assertTrue(response.isLast());
    }

    @Test
    void findAllCategories_EmptyPage() {

        Page<Category> emptyPage = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);

        when(categoryRepo.findAll(any(Pageable.class))).thenReturn(emptyPage);

        PageableResponse<CategoryDto> response = categoryService.findAllCategories(0, 10);

        assertNotNull(response);
        assertTrue(response.getContent().isEmpty());
        assertEquals(0, response.getTotalElements());
        assertEquals(0, response.getTotalPages());
        assertTrue(response.isLast());
    }

    @Test
    void findAllCategories_MultiplePages() {

        List<Category> categories = List.of(category);
        Page<Category> categoryPage = new PageImpl<>(categories, PageRequest.of(0, 1), 2);

        when(categoryRepo.findAll(any(Pageable.class))).thenReturn(categoryPage);
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        PageableResponse<CategoryDto> response = categoryService.findAllCategories(0, 1);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals(0, response.getPageNo());
        assertEquals(1, response.getPageSize());
        assertEquals(2, response.getTotalElements());
        assertEquals(2, response.getTotalPages());
        assertFalse(response.isLast());
    }
}
