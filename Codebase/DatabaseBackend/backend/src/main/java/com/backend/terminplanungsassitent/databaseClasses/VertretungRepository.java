package com.backend.terminplanungsassitent.databaseClasses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VertretungRepository extends JpaRepository<Vertretung, Integer> {

}
