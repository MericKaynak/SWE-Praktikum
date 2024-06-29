package com.backend.terminplanungsassitent.databaseClasses;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LehrveranstaltungRepository extends JpaRepository<Lehrveranstaltung, Long> {
   
    @Query("SELECT lv FROM Lehrveranstaltung lv WHERE lv.lehrperson.id = :lehrpersonId")
    List<Lehrveranstaltung> findByLehrpersonId(@Param("lehrpersonId") Integer integer);

    @Query("SELECT lv FROM Lehrveranstaltung lv WHERE lv.lehrperson.id = NULL")
    List<Lehrveranstaltung> findLehrveranstaltungWithoutLehrperson();

    @Query("SELECT lv FROM Lehrveranstaltung lv WHERE lv.termin = :termin AND lv.id <> :id")
    List<Lehrveranstaltung> findByTerminAndExcludeCurrent(@Param("termin") Termin termin, @Param("id") Integer id);

}
