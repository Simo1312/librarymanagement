package com.librarymanagement.librarymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RentalDto {

    private Long id;
    @NotBlank
    private LocalDate startDate;
    private LocalDate endDate;
    @NotBlank
    private Long bookId;


}
