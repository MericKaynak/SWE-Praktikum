package com.backend.terminplanungsassitent.databaseClasses;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerwalterRepository extends JpaRepository<Termin, Long> {

}
