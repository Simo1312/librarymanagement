package com.librarymanagement.librarymanagement.controllers;

import com.librarymanagement.librarymanagement.dto.BookDto;

import com.librarymanagement.librarymanagement.dto.PageableResponse;
import com.librarymanagement.librarymanagement.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {


 private final BookService bookService;


    @PostMapping("api/admin/book/add")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookResponseDto) {

        BookDto savedBook = bookService.addBook(bookResponseDto);
        return ResponseEntity.ok(savedBook);
    }

    @PostMapping("/api/admin//book/delete/{id}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable Long id) {

        BookDto deletedBook = bookService.deleteBook(id);
        return ResponseEntity.ok(deletedBook);
    }
    @GetMapping("/books")
    public ResponseEntity<PageableResponse<BookDto>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(bookService.findAllBooks(page, size));
    }

    @GetMapping("/books/{authorName}")
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@PathVariable String authorName) {
        List<BookDto> books = bookService.findBooksByAuthor(authorName);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/{title}")
    public ResponseEntity<BookDto> getBookByTitle(@PathVariable String title) {
        BookDto book = bookService.findBookByTitle(title);
        return ResponseEntity.ok(book);
    }
}
