package com.backend.terminplanungsassitent.databaseClasses;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LehrveranstaltungRepository extends JpaRepository<Lehrveranstaltung, Long> {

    public Lehrveranstaltung findByLehrperson(Lehrperson lehrperson);
   
    @Query("SELECT lv FROM Lehrveranstaltung lv WHERE lv.lehrperson.id = :lehrpersonId")
    List<Lehrveranstaltung> findByLehrpersonId(@Param("lehrpersonId") Long lehrpersonId);

}
