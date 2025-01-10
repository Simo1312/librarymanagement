package com.librarymanagement.librarymanagement.services;

import com.librarymanagement.librarymanagement.dto.PageableResponse;
import com.librarymanagement.librarymanagement.dto.RentalDto;

import com.librarymanagement.librarymanagement.models.Book;
import com.librarymanagement.librarymanagement.models.Rental;
import com.librarymanagement.librarymanagement.models.User;
import com.librarymanagement.librarymanagement.repositories.BookRepo;
import com.librarymanagement.librarymanagement.repositories.RentalRepo;
import com.librarymanagement.librarymanagement.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepo rentalRepository;
    private final BookRepo bookRepository;
    private final UserRepo userRepository;
    private final ModelMapper modelMapper;

    public RentalDto rentBook(Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("No authentication found - user not logged in");
        }

        Object principal = authentication.getPrincipal();
        if (principal == null) {
            throw new RuntimeException("No principal found in authentication");
        }

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));


        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (!Boolean.TRUE.equals(book.getAvailability())) {
            throw new RuntimeException("Book is not available for rent");
        }



        Rental rental = new Rental();
        rental.setBook(book);
        rental.setUser(user);
        rental.setStartDate(LocalDate.now());
        rental.setEndDate(LocalDate.now().plusWeeks(2));

        Rental savedRental = rentalRepository.save(rental);

        book.setAvailability(false);
        bookRepository.save(book);

        return toDto(savedRental);
    }

    public void returnBook(Long rentalId) {

        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        Book book = rental.getBook();
        book.setAvailability(true);
        bookRepository.save(book);

        rentalRepository.delete(rental);
    }

    public List<RentalDto> getUserRentals() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("No authentication found - user not logged in");
        }

        Object principal = authentication.getPrincipal();
        if (principal == null) {
            throw new RuntimeException("No principal found in authentication");
        }

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Rental> rentals = rentalRepository.findByUser(user);
        return rentals.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PageableResponse<RentalDto> getAllRentals(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Rental> rentalPage = rentalRepository.findAll(pageable);

        List<RentalDto> rentalDtos = rentalPage.getContent().stream()
                .map(this::toDto)
                .toList();

        PageableResponse<RentalDto> response = new PageableResponse<>();
        response.setContent(rentalDtos);
        response.setPageNo(rentalPage.getNumber());
        response.setPageSize(rentalPage.getSize());
        response.setTotalElements(rentalPage.getTotalElements());
        response.setTotalPages(rentalPage.getTotalPages());
        response.setLast(rentalPage.isLast());

        return response;
    }


    private RentalDto toDto(Rental rental) {
        return modelMapper.map(rental, RentalDto.class);
    }

    private Rental toEntity(RentalDto dto, User user) {
        Rental rental = modelMapper.map(dto, Rental.class);
        rental.setUser(user);
        return rental;
    }
}
