package com.backend.terminplanungsassitent.exceptions;

public class LehrveranstaltungNotFoundException extends RuntimeException {

    public LehrveranstaltungNotFoundException(Long id) {
        super("Lehrveranstaltung " + id + " konnte nicht gefunden werden.");
    }
    
}
