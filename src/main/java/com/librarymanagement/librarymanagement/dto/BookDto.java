package com.librarymanagement.librarymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class BookDto {

    private Long id;
    @NotBlank
    private String title;

    @NotBlank
    private LocalDate publishedYear;
    @NotBlank
    private Integer priceBgn;
    @NotBlank
    private Boolean availability;
    @NotBlank
    private String isbn;

    @NotBlank
    private String categoryName;

    @NotEmpty
    private Set<String> authorNames;
}
