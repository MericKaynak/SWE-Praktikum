package com.backend.terminplanungsassitent.databaseClasses;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "Benachrichtigung")
public class Benachrichtigung {
    @Id
    private Integer id;

    @Column(name = "Nachrichtentyp")
    private String nachrichtentyp;

    @Column(name = "Text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "Empfänger_ID")
    private Student empfänger;

    @ManyToOne
    @JoinColumn(name = "Termin_ID")
    private Termin termin;

    // Getters and Setters
}
