package com.backend.terminplanungsassitent.databaseClasses;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "Student")
public class Student {
    @Id
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    private String email;

    @Column(name = "Studiengang")
    private String studiengang;

    // Getters and Setters
}
