package com.librarymanagement.librarymanagement.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String name;

    /*@OneToMany(mappedBy = "category")
    private List<Book> books = new ArrayList<>();*/
}
