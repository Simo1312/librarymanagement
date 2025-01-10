package com.librarymanagement.librarymanagement.controllers;

import com.librarymanagement.librarymanagement.dto.AuthorDto;

import com.librarymanagement.librarymanagement.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/api/admin/author/add")
    public ResponseEntity<AuthorDto> addAuthor(@RequestBody AuthorDto authorResponseDto) {
        AuthorDto savedAuthor = authorService.addAuthor(authorResponseDto);
        return ResponseEntity.ok(savedAuthor);
    }

    @PostMapping("/api/admin/author/delete/{id}")
    public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable Long id) {

        AuthorDto deletedAuthor = authorService.deleteAuthor(id);
        return ResponseEntity.ok(deletedAuthor);
    }

    @PutMapping ("/api/admin/author/update/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorResponseDto) {

        AuthorDto updatedAuthor = authorService.updateAuthor(id, authorResponseDto);
        return ResponseEntity.ok(updatedAuthor);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<AuthorDto> findAuthorById(@PathVariable Long id) {
        AuthorDto author = authorService.findAuthorById(id);
        return ResponseEntity.ok(author);
    }

}
