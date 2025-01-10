package com.librarymanagement.librarymanagement.config;

import com.librarymanagement.librarymanagement.dto.BookDto;
import com.librarymanagement.librarymanagement.dto.RentalDto;
import com.librarymanagement.librarymanagement.models.*;
import com.librarymanagement.librarymanagement.repositories.AuthorRepo;
import com.librarymanagement.librarymanagement.repositories.BookRepo;
import com.librarymanagement.librarymanagement.repositories.CategoryRepo;
import com.librarymanagement.librarymanagement.repositories.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {

    private final CategoryRepo categoryRepository;
    private final AuthorRepo authorRepository;
    private final BookRepo bookRepository;
    private final UserRepo userRepo;

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }
}
