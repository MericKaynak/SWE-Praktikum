package com.terminplanung.databaseClasses;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TerminplanRepository extends JpaRepository<Terminplan, Long> {
    
}
