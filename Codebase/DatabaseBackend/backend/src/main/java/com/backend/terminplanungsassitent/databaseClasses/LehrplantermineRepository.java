package com.backend.terminplanungsassitent.databaseClasses;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LehrplantermineRepository extends JpaRepository<Lehrplantermine, Integer> {
    /**
     * @returns
     *          String lehrveranstaltung -> row[0]
     *          String lehrperson -> row[1]
     *          String raum -> row[2]
     *          String wochentag -> row[3]
     *          LocalTime zeitraumStart -> row[4]
     *          LocalTime zeitraumEnd -> row[5]
     *          LocalDate datum -> row[6]
     */
    @Query("SELECT lv.titel AS lehrveranstaltung, " +
            "lp.name AS lehrperson, " +
            "r.bezeichnung AS raum, " +
            "t.wochentag, " +
            "t.zeitraumStart, " +
            "t.zeitraumEnd, " +
            "lpt.datum " +
            "FROM Lehrplantermine lpt " +
            "JOIN lpt.lehrveranstaltung lv " +
            "JOIN lv.lehrperson lp " +
            "JOIN lv.raum r " +
            "JOIN lv.termin t " +
            "WHERE lp.name = :lehrpersonName")
    List<Object[]> findLehrveranstaltungenByLehrpersonName(@Param("lehrpersonName") String lehrpersonName);
}
