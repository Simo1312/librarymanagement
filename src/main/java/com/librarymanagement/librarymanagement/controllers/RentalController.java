package com.librarymanagement.librarymanagement.controllers;

import com.librarymanagement.librarymanagement.dto.PageableResponse;
import com.librarymanagement.librarymanagement.dto.RentalDto;
import com.librarymanagement.librarymanagement.services.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/rent")
    public ResponseEntity<RentalDto> rentBook(@RequestParam Long bookId) {
        RentalDto rentalDto = rentalService.rentBook(bookId);
        return ResponseEntity.ok(rentalDto);
    }

    @GetMapping("/my-rentals")
    public ResponseEntity<List<RentalDto>> getMyRentals() {
        List<RentalDto> rentals = rentalService.getUserRentals();
        return ResponseEntity.ok(rentals);
    }

    @PostMapping("/return/{rentalId}")
    public ResponseEntity<String> returnBook(@PathVariable Long rentalId) {
        rentalService.returnBook(rentalId);
        return ResponseEntity.ok("Book returned successfully");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableResponse<RentalDto>> getAllRentals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(rentalService.getAllRentals(page, size));
    }
}
