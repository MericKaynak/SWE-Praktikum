package com.backend.terminplanungsassitent.databaseClasses;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BesuchenRepository extends JpaRepository<Besuchen, Integer> {

    @Query("SELECT b FROM Besuchen b WHERE b.lehrveranstaltung.id = :lehrveranstaltungId")
    List<Besuchen> findAllByLehrveranstaltungId(@Param("lehrveranstaltungId") Integer lehrveranstaltungId);

}
