package com.terminplanung.databaseClasses;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LehrveranstaltungRepository extends JpaRepository<Lehrveranstaltung, Integer> {

    public Lehrveranstaltung findByLehrperson(Lehrperson lehrperson);
   
    @Query("SELECT lv FROM Lehrveranstaltung lv WHERE lv.lehrperson.id = :lehrpersonId")
    List<Lehrveranstaltung> findByLehrpersonId(@Param("lehrpersonId") Integer lehrpersonId);

}
