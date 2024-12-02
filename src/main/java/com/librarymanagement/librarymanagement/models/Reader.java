package com.librarymanagement.librarymanagement.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Reader")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reader_name")
    private String name;

    @Column(name = "reader_email")
    private String email;

    /*@OneToMany(mappedBy = "reader")
    private List<Rental> rentals = new ArrayList<>();*/
}
