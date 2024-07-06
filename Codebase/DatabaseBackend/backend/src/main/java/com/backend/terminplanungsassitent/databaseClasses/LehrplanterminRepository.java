package com.backend.terminplanungsassitent.databaseClasses;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LehrplanterminRepository extends JpaRepository<Lehrplantermin, Integer> {
        /*
         * @returns
         * String lehrveranstaltung -> row[0]
         * String lehrperson -> row[1]
         * String raum -> row[2]
         * String wochentag -> row[3]
         * LocalTime zeitraumStart -> row[4]
         * LocalTime zeitraumEnd -> row[5]
         * LocalDate datum -> row[6]
         */
        /*
         * @Query("SELECT lv.titel AS lehrveranstaltung, " +
         * "lp.name AS lehrperson, " +
         * "r.bezeichnung AS raum, " +
         * "t.wochentag, " +
         * "t.zeitraumStart, " +
         * "t.zeitraumEnd, " +
         * "lpt.datum " +
         * "FROM Lehrplantermin lpt " +
         * "JOIN lpt.lehrveranstaltung lv " +
         * "JOIN lv.lehrperson lp " +
         * "JOIN lv.raum r " +
         * "JOIN lv.termin t " +
         * "WHERE lp.name = :lehrpersonName")
         * List<Object[]>
         * findLehrveranstaltungenByLehrpersonName(@Param("lehrpersonName") String
         * lehrpersonName);
         */

        // Or, using @Query annotation
        @Query("SELECT lv FROM Lehrveranstaltung lv WHERE lv.lehrperson.id = :lehrpersonId")
        List<Lehrveranstaltung> findLehrveranstaltungenByLehrpersonId(@Param("lehrpersonId") Integer lehrpersonId);

        // Using @Query annotation
        @Query("SELECT lpt FROM Lehrplantermin lpt WHERE lpt.lehrveranstaltung.lehrperson.id = :lehrpersonId")
        List<Lehrplantermin> findLehrplantermineByLehrpersonId(@Param("lehrpersonId") Integer lehrpersonId);

        @Query("SELECT l FROM Lehrplantermin l WHERE l.datum BETWEEN :startDate AND :endDate AND l.lehrveranstaltung.lehrperson.id = :lehrpersonId")
        List<Lehrplantermin> findAllByDatumBetweenAndLehrpersonId(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("lehrpersonId") Integer lehrpersonId);

        @Query("SELECT lt FROM Lehrplantermin lt INNER JOIN Lehrveranstaltung lv ON lt.lehrveranstaltung.id = lv.id " +
                        "INNER JOIN Vertretung v ON lv.id = v.lehrveranstaltung.id " +
                        "WHERE lv.lehrperson.id = :lehrpersonId " +
                        "ORDER BY lt.datum, lt.id")
        List<Lehrplantermin> findByLehrpersonID(@Param("lehrpersonId") Integer lehrpersonId);

        @Query("SELECT s.email FROM Student s " +
                        "JOIN Besuchen b ON s.id = b.student.id " +
                        "JOIN Lehrveranstaltung lv ON b.lehrveranstaltung.id = lv.id " +
                        "JOIN Vertretung v ON lv.id = v.lehrveranstaltung.id " +
                        "JOIN Lehrperson lp ON v.lehrperson.id = lp.id")

        List<String> findStudentEmailsByVertretungId(@Param("vertretungId") Integer vertretungId);

        @Query("SELECT lp " +
                "FROM Lehrplantermin lp " +
                "WHERE lp.vertretung.lehrperson.id = :lehrpersonId ")
        List<Lehrplantermin> findVertretendeLehrplantermineByLehrpersonID(@Param("lehrpersonId") Integer lehrpersonId);
}