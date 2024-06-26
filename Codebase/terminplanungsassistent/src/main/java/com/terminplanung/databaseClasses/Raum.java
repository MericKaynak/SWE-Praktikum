package com.terminplanung.databaseClasses;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Raum {
    
    @Id
    private int id;
    
    private String bezeichnung;
    
    private int kapazitat;
    
    private String standort;
    
    @OneToMany(mappedBy = "raum")
    private Set<Terminplan> terminplane;
    
    @OneToMany(mappedBy = "raum")
    private Set<Lehrveranstaltung> lehrveranstaltungen;

    // Getters and Setters
}
