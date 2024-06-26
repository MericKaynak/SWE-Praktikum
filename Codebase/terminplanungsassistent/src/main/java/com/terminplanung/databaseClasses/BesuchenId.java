package com.terminplanung.databaseClasses;

import java.io.Serializable;
import java.util.Objects;

public class BesuchenId implements Serializable {

    private int student;
    private int lehrveranstaltung;

    public BesuchenId() {
    }

    public BesuchenId(int student, int lehrveranstaltung) {
        this.student = student;
        this.lehrveranstaltung = lehrveranstaltung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BesuchenId that = (BesuchenId) o;
        return student == that.student && lehrveranstaltung == that.lehrveranstaltung;
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, lehrveranstaltung);
    }
}
