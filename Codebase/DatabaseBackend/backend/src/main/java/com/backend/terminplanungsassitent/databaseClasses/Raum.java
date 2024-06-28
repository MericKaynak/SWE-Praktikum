package com.backend.terminplanungsassitent.databaseClasses;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "Raum")
public class Raum {
    @Id
    private Integer id;

    @Column(name = "Bezeichnung")
    private String bezeichnung;

    @Column(name = "Kapazität")
    private Integer kapazität;

    @Column(name = "Standort")
    private String standort;

    // Getters and Setters
}
