package com.backend.terminplanungsassitent.exceptions;

public class TerminNotFoundException extends RuntimeException {

    public TerminNotFoundException(Integer id) {
        super("Could not find calendar " + id);
    }
    
}
