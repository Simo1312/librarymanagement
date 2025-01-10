package com.librarymanagement.librarymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;



}
