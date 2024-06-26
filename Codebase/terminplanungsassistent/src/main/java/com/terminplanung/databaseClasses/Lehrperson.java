package com.terminplanung.databaseClasses;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Lehrperson {
    
    @Id
    private int id;
    
    private String name;
    
    private String email;
    
    private String rolle;
    
    private String verfugbarkeit;
    
    @OneToMany(mappedBy = "lehrperson")
    private Set<Lehrveranstaltung> lehrveranstaltungen;

    // Getters and Setters
}
