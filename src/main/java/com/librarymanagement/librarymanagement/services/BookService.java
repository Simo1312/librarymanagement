package com.librarymanagement.librarymanagement.services;

import com.librarymanagement.librarymanagement.dto.BookDto;
import com.librarymanagement.librarymanagement.dto.PageableResponse;
import com.librarymanagement.librarymanagement.models.Author;
import com.librarymanagement.librarymanagement.models.Book;
import com.librarymanagement.librarymanagement.models.Category;
import com.librarymanagement.librarymanagement.repositories.AuthorRepo;
import com.librarymanagement.librarymanagement.repositories.BookRepo;
import com.librarymanagement.librarymanagement.repositories.CategoryRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepo bookRepository;
    private final ModelMapper modelMapper;
    private final AuthorRepo authorRepository;
    private final CategoryRepo categoryRepository;


    public BookService(BookRepo bookRepository, ModelMapper modelMapper, AuthorRepo authorRepo, CategoryRepo categoryRepo) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepo;
        this.categoryRepository = categoryRepo;
    }
    @Transactional
    public BookDto addBook(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);

        if (bookDto.getCategoryName() != null) {
            Category category = categoryRepository.findByName(bookDto.getCategoryName())
                    .orElseThrow(() -> new RuntimeException("Category not found: " + bookDto.getCategoryName()));
            book.setCategory(category);
        } else {
            throw new RuntimeException("Category is required");
        }

        if (bookDto.getAuthorNames() != null && !bookDto.getAuthorNames().isEmpty()) {

            Set<Author> authors = new HashSet<>();

            for (String authorName : bookDto.getAuthorNames()) {
                Author author = authorRepository.findByName(authorName)
                        .orElseThrow(() -> new RuntimeException("Author not found: " + authorName));
                authors.add(author);
            }

            book.setAuthors(authors);
        } else {
            throw new RuntimeException("At least one author is required");
        }

        Book savedBook = bookRepository.save(book);
        return modelMapper.map(savedBook, BookDto.class);
    }


    public BookDto deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);
        return modelMapper.map(book, BookDto.class);
    }
    public BookDto findBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();
        return modelMapper.map(book, BookDto.class);
    }

    public BookDto findBookByTitle(String title) {
        Book book = bookRepository.findByTitle(title).orElseThrow();
        return modelMapper.map(book, BookDto.class);
    }

    public List<BookDto> findBooksByAuthor(String authorName) {
        List<Book> books = bookRepository.findByAuthor(authorName);

        // Convert to DTOs
        return books.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    public PageableResponse<BookDto> findAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);

        List<BookDto> bookDtos = bookPage.getContent().stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .toList();

        PageableResponse<BookDto> response = new PageableResponse<>();
        response.setContent(bookDtos);
        response.setPageNo(bookPage.getNumber());
        response.setPageSize(bookPage.getSize());
        response.setTotalElements(bookPage.getTotalElements());
        response.setTotalPages(bookPage.getTotalPages());
        response.setLast(bookPage.isLast());

        return response;
    }


}
