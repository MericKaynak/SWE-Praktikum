package com.exceptions;

public class TerminNotFoundException extends RuntimeException {

    public TerminNotFoundException(Integer id) {
        super("Could not find calendar " + id);
    }
    
}
