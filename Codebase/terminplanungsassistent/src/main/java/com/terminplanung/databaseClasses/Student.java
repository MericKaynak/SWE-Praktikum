package com.terminplanung.databaseClasses;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Student {
    
    @Id
    private int id;
    
    private String name;
    
    private String email;
    
    private String studiengang;
    
    @OneToMany(mappedBy = "student")
    private Set<Besuchen> besuchen;

    // Getters and Setters
}
