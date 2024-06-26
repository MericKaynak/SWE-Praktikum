package com.terminplanung.databaseClasses;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Terminplan {
    
    @Id
    private int id;
    
    private String name;
    
    @Column(name = "zeitraum_start")
    private java.sql.Date zeitraumStart;
    
    @Column(name = "zeitraum_end")
    private java.sql.Date zeitraumEnd;
    
    @ManyToOne
    @JoinColumn(name = "raum_id")
    private Raum raum;
    
    @OneToMany(mappedBy = "terminplan")
    private Set<Lehrveranstaltung> lehrveranstaltungen;
    
    @OneToMany(mappedBy = "terminplan")
    private Set<Benachrichtigung> benachrichtigungen;

    // Getters and Setters
}
