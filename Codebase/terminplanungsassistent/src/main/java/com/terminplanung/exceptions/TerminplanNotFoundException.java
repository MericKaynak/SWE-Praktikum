package com.terminplanung.exceptions;

public class TerminplanNotFoundException extends RuntimeException {

    public TerminplanNotFoundException(Long id) {
        super("Could not find calendar " + id);
    }
    
}
