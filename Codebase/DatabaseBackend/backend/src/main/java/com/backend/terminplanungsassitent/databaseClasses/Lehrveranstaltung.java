package com.backend.terminplanungsassitent.databaseClasses;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import com.backend.terminplanungsassitent.RESTController.TimeComparison;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;

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

        /**
         * If the Standort of the calling object is different from the passed object,
         * check whether the passed object has a Termin that happens within 2 hours of
         * the calling object's Termin.
         * 
         * @param lehrveranstaltung
         * @return
         */
        public boolean checkTravelTimeConflict(Lehrveranstaltung lehrveranstaltung) {
                return ((this.getRaum().getStandort() != lehrveranstaltung.getRaum().getStandort()) &&
                                this.getTermin().getWochentag() == lehrveranstaltung.getTermin().getWochentag() &&
                                (TimeComparison
                                                .areTimesWithinTwoHours(
                                                                lehrveranstaltung.getTermin().getZeitraumStart(),
                                                                this.getTermin().getZeitraumEnd())
                                                || TimeComparison
                                                                .areTimesWithinTwoHours(
                                                                                lehrveranstaltung.getTermin()
                                                                                                .getZeitraumEnd(),
                                                                                this.getTermin().getZeitraumStart())));
        }

        /**
         * Check whether the Lehrperson for passed Lehrveranstaltung is the same as the
         * calling object's.
         * 
         * @param lehrveranstaltung
         * @param lehrperson
         * @return true if same Lehrperson
         */
        public boolean checkSameLehrperson(Lehrveranstaltung lehrveranstaltung, Lehrperson lehrperson) {
                return lehrperson == lehrveranstaltung.getLehrperson();
        }

        // Getters and Setters
}
