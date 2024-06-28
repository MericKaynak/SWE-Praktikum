package com.databaseClasses;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "Lehrveranstaltung")
public class Lehrveranstaltung {
    @Id
    private Integer id;

    @Column(name = "Titel")
    private String titel;

    @Column(name = "Fachbereich")
    private String fachbereich;

    @Column(name = "Dauer")
    private Integer dauer;

    @ManyToOne
    @JoinColumn(name = "Raum_ID")
    private Raum raum;

    @ManyToOne
    @JoinColumn(name = "Termin_ID")
    private Termin termin;

    @ManyToOne
    @JoinColumn(name = "Lehrperson_ID")
    private Lehrperson lehrperson;

    // Getters and Setters
}
