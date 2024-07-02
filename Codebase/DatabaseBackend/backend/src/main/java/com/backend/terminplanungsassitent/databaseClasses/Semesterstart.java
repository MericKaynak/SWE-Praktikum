package com.backend.terminplanungsassitent.databaseClasses;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Semesterstart")
public class Semesterstart {

    @Id
    private Integer id;

    @Column(name = "Startdatum")
    private LocalDate Startdatum;

}
