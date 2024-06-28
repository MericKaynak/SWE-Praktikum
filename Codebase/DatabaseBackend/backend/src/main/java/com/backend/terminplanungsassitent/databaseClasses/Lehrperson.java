package com.backend.terminplanungsassitent.databaseClasses;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "Lehrperson")
public class Lehrperson {
    @Id
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    private String email;

    @Column(name = "Rolle")
    private String rolle;

    @Column(name = "Verfügbarkeit")
    private String verfügbarkeit;

    // Getters and Setters
}
