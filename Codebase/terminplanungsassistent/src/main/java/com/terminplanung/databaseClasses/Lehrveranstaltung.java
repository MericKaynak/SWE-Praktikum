package com.terminplanung.databaseClasses;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Lehrveranstaltung {
    
    @Id
    private int id;
    
    private String titel;
    
    private String fachbereich;
    
    private int dauer;
    
    @ManyToOne
    @JoinColumn(name = "raum_id")
    private Raum raum;
    
    @ManyToOne
    @JoinColumn(name = "terminplan_id")
    private Terminplan terminplan;
    
    @ManyToOne
    @JoinColumn(name = "betreuende_person_id")
    private Lehrperson lehrperson;
    
    @OneToMany(mappedBy = "lehrveranstaltung")
    private Set<Besuchen> besuchen;

    // Getters and Setters
}
