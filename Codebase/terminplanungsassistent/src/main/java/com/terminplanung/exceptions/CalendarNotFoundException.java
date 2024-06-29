package com.terminplanung.exceptions;

public class CalendarNotFoundException extends RuntimeException {

    CalendarNotFoundException(Long id) {
        super("Could not find calendar " + id);
    }
    
}
