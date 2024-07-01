package com.backend.terminplanungsassitent.exceptions;

public class TerminNotFoundException extends RuntimeException {

    public TerminNotFoundException(Long id) {
        super("Could not find calendar " + id);
    }
    
}
