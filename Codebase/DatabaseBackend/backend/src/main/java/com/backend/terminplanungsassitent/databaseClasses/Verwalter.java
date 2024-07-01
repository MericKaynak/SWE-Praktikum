package com.backend.terminplanungsassitent.databaseClasses;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Verwalter")
public class Verwalter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    private String email;

    @Column(name = "Passwort")
    private String passwort;

}