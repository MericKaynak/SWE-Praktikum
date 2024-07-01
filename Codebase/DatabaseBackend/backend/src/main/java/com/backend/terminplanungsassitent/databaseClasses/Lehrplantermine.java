package com.backend.terminplanungsassitent.databaseClasses;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.util.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "Lehrplantermine")
public class Lehrplantermine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Lehrveranstaltung_ID")
    private int lehrveranstaltungId;

    @Column(name = "Datum")
    private Date datum;

    @ManyToOne
    @JoinColumn(name = "Lehrveranstaltung_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Lehrveranstaltung lehrveranstaltung;

    // Getters and Setters
}
