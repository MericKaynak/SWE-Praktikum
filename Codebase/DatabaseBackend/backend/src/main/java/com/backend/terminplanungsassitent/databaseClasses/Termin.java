package com.backend.terminplanungsassitent.databaseClasses;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "Termin")
public class Termin {
    @Id
    private Integer id;

    @Column(name = "Datum")
    private LocalDate datum;

    @Column(name = "Zeitraum_Start")
    private LocalTime zeitraumStart;

    @Column(name = "Zeitraum_End")
    private LocalTime zeitraumEnd;

    @ManyToOne
    @JoinColumn(name = "Raum_ID")
    private Raum raum;

    // Getters and Setters
}
