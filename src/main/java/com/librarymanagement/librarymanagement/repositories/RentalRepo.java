package com.librarymanagement.librarymanagement.repositories;

import com.librarymanagement.librarymanagement.models.Rental;
import com.librarymanagement.librarymanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepo extends JpaRepository<Rental, Long> {
    List<Rental> findByUser(User user);
}
