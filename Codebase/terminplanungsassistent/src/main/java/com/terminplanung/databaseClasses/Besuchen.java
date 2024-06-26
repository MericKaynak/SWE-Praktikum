package com.terminplanung.databaseClasses;

import jakarta.persistence.*;

@Entity
@IdClass(BesuchenId.class)
public class Besuchen {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "lehrveranstaltung_id")
    private Lehrveranstaltung lehrveranstaltung;

    // Getters and Setters
}
