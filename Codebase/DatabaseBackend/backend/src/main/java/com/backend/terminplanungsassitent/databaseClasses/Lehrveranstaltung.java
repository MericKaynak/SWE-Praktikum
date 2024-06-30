package com.backend.terminplanungsassitent.databaseClasses;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import com.backend.terminplanungsassitent.terminplanung.TimeComparison;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Data
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

    public boolean checkTravelTimeConflict(Lehrveranstaltung lehrveranstaltung) {
        return ((this.getRaum().getStandort() != lehrveranstaltung.getRaum().getStandort()) &&
                (TimeComparison
                        .areTimesWithinTwoHours(lehrveranstaltung.getTermin().getZeitraumStart(),
                                this.getTermin().getZeitraumEnd())
                        || TimeComparison
                                .areTimesWithinTwoHours(lehrveranstaltung.getTermin().getZeitraumEnd(),
                                        this.getTermin().getZeitraumStart())));
    }

    public boolean checkSameLehrperson(Lehrveranstaltung lehrveranstaltung, Lehrperson lehrperson) {
        return lehrperson == lehrveranstaltung.getLehrperson();
    }

    // Getters and Setters
}
