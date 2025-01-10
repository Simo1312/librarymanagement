package com.librarymanagement.librarymanagement.services;

import com.librarymanagement.librarymanagement.dto.RentalDto;
import com.librarymanagement.librarymanagement.models.Book;
import com.librarymanagement.librarymanagement.models.Rental;
import com.librarymanagement.librarymanagement.models.User;
import com.librarymanagement.librarymanagement.repositories.BookRepo;
import com.librarymanagement.librarymanagement.repositories.RentalRepo;
import com.librarymanagement.librarymanagement.repositories.UserRepo;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.Collections;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {
    @Mock
    private RentalRepo rentalRepository;
    @Mock
    private BookRepo bookRepository;
    @Mock
    private UserRepo userRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private RentalService rentalService;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;

    private User user;
    private Book book;
    private Rental rental;
    private RentalDto rentalDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");

        book = new Book();
        book.setId(1L);
        book.setAvailability(true);

        rental = new Rental();
        rental.setId(1L);
        rental.setUser(user);
        rental.setBook(book);
        rental.setStartDate(LocalDate.now());
        rental.setEndDate(LocalDate.now().plusWeeks(2));

        rentalDto = new RentalDto();
        rentalDto.setId(1L);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void rentBook_Success() {
        when(authentication.getPrincipal()).thenReturn(new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.emptyList();
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return "testUser";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        });
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(rentalRepository.save(any(Rental.class))).thenReturn(rental);
        when(modelMapper.map(rental, RentalDto.class)).thenReturn(rentalDto);

        RentalDto result = rentalService.rentBook(1L);

        assertNotNull(result);
        verify(bookRepository).save(book);
        assertFalse(book.getAvailability());
    }

    @Test
    void rentBook_BookNotAvailable() {
        book.setAvailability(false);
        when(authentication.getPrincipal()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThrows(RuntimeException.class, () -> rentalService.rentBook(1L));
    }


    @Test
    void getUserRentals_Success() {
        when(authentication.getPrincipal()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(rentalRepository.findByUser(user)).thenReturn(List.of(rental));
        when(modelMapper.map(rental, RentalDto.class)).thenReturn(rentalDto);

        List<RentalDto> results = rentalService.getUserRentals();

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void rentBook_UserNotFound() {
        when(authentication.getPrincipal()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> rentalService.rentBook(1L));
    }


}
