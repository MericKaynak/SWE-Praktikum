package com.backend.terminplanungsassitent.databaseClasses;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.util.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "Vertretung")
public class Vertretung {

    @Id
    @Column(name = "ID")
    private Integer ID;

    @ManyToOne
    @JoinColumn(name = "Lehrveranstaltung_ID")
    private Lehrveranstaltung lehrveranstaltung;

    @Column(name = "Datum")
    private Date datum;

    @ManyToOne
    @JoinColumn(name = "Lehrperson_ID")
    private Lehrperson lehrperson;

    // Getters and Setters
}
