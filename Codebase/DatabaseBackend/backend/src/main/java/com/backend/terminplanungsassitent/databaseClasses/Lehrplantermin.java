package com.backend.terminplanungsassitent.databaseClasses;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "Lehrplantermin")
public class Lehrplantermin {

    @Id
    @Column(name = "ID")
    Integer id;

    @Column(name = "Datum")
    private LocalDate datum;

    @ManyToOne
    @JoinColumn(name = "Lehrveranstaltung_ID")
    private Lehrveranstaltung lehrveranstaltung;

    // Getters and Setters
}
