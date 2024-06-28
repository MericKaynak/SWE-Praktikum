package com.databaseClasses;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.io.Serializable;

@Entity
@Table(name = "Besuchen")
@IdClass(BesuchenId.class)
public class Besuchen implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "Student_ID")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "Lehrveranstaltung_ID")
    private Lehrveranstaltung lehrveranstaltung;

    // Getters and Setters
}

class BesuchenId implements Serializable {
    private Integer student;
    private Integer lehrveranstaltung;

    // Getters, Setters, hashCode, equals
}
