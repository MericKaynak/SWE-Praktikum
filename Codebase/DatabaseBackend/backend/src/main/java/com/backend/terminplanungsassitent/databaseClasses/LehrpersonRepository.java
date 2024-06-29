package com.backend.terminplanungsassitent.databaseClasses;


import org.springframework.data.jpa.repository.JpaRepository;

public interface LehrpersonRepository extends JpaRepository<Lehrperson, Long> {
    
}
