package com.backend.terminplanungsassitent.exceptions;

public class LehrpersonNotFoundException extends RuntimeException {

    public LehrpersonNotFoundException(Integer id) {
        super("Lehrperson " + id + " konnte nicht gefunden werden.");
    }

}
