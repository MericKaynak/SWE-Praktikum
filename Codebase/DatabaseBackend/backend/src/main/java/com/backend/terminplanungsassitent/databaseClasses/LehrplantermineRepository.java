package com.backend.terminplanungsassitent.databaseClasses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LehrplantermineRepository extends JpaRepository<Lehrplantermine, Integer> {
    /*
    * String lehrveranstaltung = (String) row[0]
    * String lehrperson = (String) row[1]
    * String raum = (String) row[2]
    * String wochentag = (String) row[3]
    * LocalTime zeitraumStart = (LocalTime) row[4]
    * LocalTime zeitraumEnd = (LocalTime) row[5]
    * LocalDate datum = (LocalDate) row[6]
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
