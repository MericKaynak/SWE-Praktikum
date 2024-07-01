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
    @Column(name = "Vertretung_ID")
    private int vertretungId;

    @Column(name = "Datum")
    private Date datum;

    @Column(name = "Lehrperson")
    private String lehrperson;

    @ManyToOne
    @JoinColumn(name = "Vertretung_ID", referencedColumnName = "ID")
    private Lehrveranstaltung lehrveranstaltung;

    // Getters and Setters
}
