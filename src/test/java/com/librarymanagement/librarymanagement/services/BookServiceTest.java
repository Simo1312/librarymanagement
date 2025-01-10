package com.librarymanagement.librarymanagement.services;

import com.librarymanagement.librarymanagement.dto.BookDto;
import com.librarymanagement.librarymanagement.dto.PageableResponse;
import com.librarymanagement.librarymanagement.models.Author;
import com.librarymanagement.librarymanagement.models.Book;
import com.librarymanagement.librarymanagement.models.Category;
import com.librarymanagement.librarymanagement.repositories.AuthorRepo;
import com.librarymanagement.librarymanagement.repositories.BookRepo;
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
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepo bookRepository;
    @Mock
    private AuthorRepo authorRepository;
    @Mock
    private CategoryRepo categoryRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDto bookDto;
    private Author author;
    private Category category;

    @BeforeEach
    void setUp() {

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAvailability(true);

        author = new Author();
        author.setId(1L);
        author.setName("Test Author");

        category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        Set<Author> authors = new HashSet<>();
        authors.add(author);
        book.setAuthors(authors);
        book.setCategory(category);

        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Test Book");
        bookDto.setCategoryName("Test Category");
        bookDto.setAuthorNames(Set.of("Test Author"));
    }

    @Test
    void addBook_Success() {
        when(categoryRepository.findByName("Test Category")).thenReturn(Optional.of(category));
        when(authorRepository.findByName("Test Author")).thenReturn(Optional.of(author));
        when(modelMapper.map(bookDto, Book.class)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        BookDto result = bookService.addBook(bookDto);

        assertNotNull(result);
        assertEquals(bookDto.getTitle(), result.getTitle());
        verify(bookRepository).save(book);
    }

    @Test
    void addBook_CategoryNotFound() {
        when(categoryRepository.findByName("Test Category")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.addBook(bookDto));
    }


    @Test
    void addBook_NullCategory() {
        bookDto.setCategoryName(null);

        assertThrows(RuntimeException.class, () -> bookService.addBook(bookDto));
    }

    @Test
    void addBook_NoAuthors() {
        bookDto.setAuthorNames(Collections.emptySet());

        assertThrows(RuntimeException.class, () -> bookService.addBook(bookDto));
    }

    @Test
    void deleteBook_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        BookDto result = bookService.deleteBook(1L);

        assertNotNull(result);
        verify(bookRepository).delete(book);
    }

    @Test
    void deleteBook_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> bookService.deleteBook(1L));
    }

    @Test
    void findBookById_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        BookDto result = bookService.findBookById(1L);

        assertNotNull(result);
        assertEquals(bookDto.getTitle(), result.getTitle());
    }

    @Test
    void findBookByTitle_Success() {
        when(bookRepository.findByTitle("Test Book")).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        BookDto result = bookService.findBookByTitle("Test Book");

        assertNotNull(result);
        assertEquals(bookDto.getTitle(), result.getTitle());
    }

    @Test
    void findBooksByAuthor_Success() {
        when(bookRepository.findByAuthor("Test Author")).thenReturn(List.of(book));
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        List<BookDto> results = bookService.findBooksByAuthor("Test Author");

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(bookDto.getTitle(), results.get(0).getTitle());
    }

    @Test
    void findAllBooks_Success() {
        Page<Book> bookPage = new PageImpl<>(List.of(book));
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        PageableResponse<BookDto> response = bookService.findAllBooks(0, 10);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals(0, response.getPageNo());
        assertEquals(1, response.getTotalElements());
        assertTrue(response.isLast());
    }

    @Test
    void findAllBooks_EmptyPage() {
        Page<Book> emptyPage = new PageImpl<>(Collections.emptyList());
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        PageableResponse<BookDto> response = bookService.findAllBooks(0, 10);

        assertNotNull(response);
        assertTrue(response.getContent().isEmpty());
        assertEquals(0, response.getTotalElements());
        assertTrue(response.isLast());
    }
}
