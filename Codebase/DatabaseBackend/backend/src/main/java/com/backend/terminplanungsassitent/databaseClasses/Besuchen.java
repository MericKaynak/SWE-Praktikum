package com.backend.terminplanungsassitent.databaseClasses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Besuchen")
public class Besuchen implements Serializable {

    @Id
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Student_ID")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "Lehrveranstaltung_ID")
    private Lehrveranstaltung lehrveranstaltung;

    // Getters and Setters
}
