package com.librarymanagement.librarymanagement.repositories;

import com.librarymanagement.librarymanagement.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);
}
