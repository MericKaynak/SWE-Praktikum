package com.backend.terminplanungsassitent.databaseClasses;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Lehrperson")
public class Lehrperson {
    @Id
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    private String email;

    @Column(name = "Rolle")
    private String rolle;

    @Column(name = "Wochenarbeitsstunden")
    private Integer Wochenarbeitsstunden;

    /**
     * Check if the Lehrperson works less or equal than 18 hours.
     * 
     * @return true if yes
     */
    public boolean istVerfuegbar() {
        return this.getWochenarbeitsstunden() < 18;
    }

    // Getters and Setters
}
