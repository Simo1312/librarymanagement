package com.librarymanagement.librarymanagement.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_name")
    private String name;

    @Column(name = "short_bio")
    private String biography;

    /*@ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();*/
}
