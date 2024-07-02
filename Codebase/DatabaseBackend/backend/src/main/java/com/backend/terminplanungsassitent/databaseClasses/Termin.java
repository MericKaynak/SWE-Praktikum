package com.backend.terminplanungsassitent.databaseClasses;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.Data;

@Data
@Entity
@Table(name = "Termin")
public class Termin {
    @Id
    private Integer id;

    @Column(name = "Wochentag")
    private String wochentag;

    @Column(name = "Zeitraum_Start")
    private LocalTime zeitraumStart;

    @Column(name = "Zeitraum_End")
    private LocalTime zeitraumEnd;

    // Getters and Setters
}
