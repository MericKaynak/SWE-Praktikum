package com.backend.terminplanungsassitent.databaseClasses;

import lombok.Data;

import jakarta.persistence.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Vertretung")
public class Vertretung {

    @Id
    @SequenceGenerator(name = "vertretung_seq", sequenceName = "vertretung_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vertretung_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Lehrveranstaltung_ID", nullable = false)
    private Lehrveranstaltung lehrveranstaltung;

    @Temporal(TemporalType.DATE)
    @Column(name = "Datum", nullable = false)
    private LocalDate datum;

    @ManyToOne
    @JoinColumn(name = "Lehrperson_ID", nullable = false)
    private Lehrperson lehrperson;

    // Getters and Setters
}
