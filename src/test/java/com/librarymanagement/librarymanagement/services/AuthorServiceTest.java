package com.librarymanagement.librarymanagement.services;

import com.librarymanagement.librarymanagement.dto.AuthorDto;
import com.librarymanagement.librarymanagement.models.Author;
import com.librarymanagement.librarymanagement.repositories.AuthorRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepo authorRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private AuthorService authorService;

    private Author author;
    private AuthorDto authorDto;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Test Author");
        author.setBiography("Test Biography");

        authorDto = new AuthorDto();
        authorDto.setId(1L);
        authorDto.setName("Test Author");
        authorDto.setBiography("Test Biography");
    }

    @Test
    void addAuthor_Success() {
        when(modelMapper.map(authorDto, Author.class)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        AuthorDto result = authorService.addAuthor(authorDto);

        assertNotNull(result);
        assertEquals(authorDto.getName(), result.getName());
        verify(authorRepository).save(author);
    }

    @Test
    void deleteAuthor_Success() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        AuthorDto result = authorService.deleteAuthor(1L);

        assertNotNull(result);
        verify(authorRepository).delete(author);
    }

    @Test
    void deleteAuthor_NotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> authorService.deleteAuthor(1L));
    }

    @Test
    void updateAuthor_Success() {
        AuthorDto updateDto = new AuthorDto();
        updateDto.setName("Updated Name");
        updateDto.setBiography("Updated Bio");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(author)).thenReturn(author);
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(updateDto);

        AuthorDto result = authorService.updateAuthor(1L, updateDto);

        assertNotNull(result);
        assertEquals(updateDto.getName(), result.getName());
        assertEquals(updateDto.getBiography(), result.getBiography());
        verify(authorRepository).save(author);
    }

    @Test
    void updateAuthor_NotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> authorService.updateAuthor(1L, authorDto));
    }

    @Test
    void findAuthorById_Success() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        AuthorDto result = authorService.findAuthorById(1L);

        assertNotNull(result);
        assertEquals(authorDto.getName(), result.getName());
    }

    @Test
    void findAuthorById_NotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> authorService.findAuthorById(1L));
    }
}
