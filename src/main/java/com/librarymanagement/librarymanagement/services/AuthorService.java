package com.librarymanagement.librarymanagement.services;

import com.librarymanagement.librarymanagement.dto.AuthorDto;
import com.librarymanagement.librarymanagement.models.Author;
import com.librarymanagement.librarymanagement.repositories.AuthorRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepo authorRepository;
    private final ModelMapper modelMapper;

    public AuthorService(AuthorRepo authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public AuthorDto addAuthor(AuthorDto authorResponseDto) {
        Author author = modelMapper.map(authorResponseDto, Author.class);
        Author saved = authorRepository.save(author);
        return modelMapper.map(saved, AuthorDto.class);
    }

    public AuthorDto deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        authorRepository.delete(author);
        return modelMapper.map(author, AuthorDto.class);
    }

    public AuthorDto updateAuthor(Long id, AuthorDto authorResponseDto) {
        Author author = authorRepository.findById(id).orElseThrow();
        author.setName(authorResponseDto.getName());
        author.setBiography(authorResponseDto.getBiography());
        Author updated = authorRepository.save(author);
        return modelMapper.map(updated, AuthorDto.class);
    }


    public AuthorDto findAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return modelMapper.map(author, AuthorDto.class);
    }
}
