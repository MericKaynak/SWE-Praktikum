package com.backend.terminplanungsassitent.exceptions;

public class LehrpersonNotFoundException extends RuntimeException {

    public LehrpersonNotFoundException(Long id) {
        super("Lehrperson " + id + " konnte nicht gefunden werden.");
    }
    
}
