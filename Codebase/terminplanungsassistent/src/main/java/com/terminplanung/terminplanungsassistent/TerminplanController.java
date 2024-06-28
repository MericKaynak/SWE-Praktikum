package com.terminplanung.terminplanungsassistent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terminplanung.databaseClasses.BenachrichtigungRepository;
import com.terminplanung.databaseClasses.BesuchenRepository;
import com.terminplanung.databaseClasses.Lehrperson;
import com.terminplanung.databaseClasses.LehrpersonRepository;
import com.terminplanung.databaseClasses.Lehrveranstaltung;
import com.terminplanung.databaseClasses.LehrveranstaltungRepository;
import com.terminplanung.databaseClasses.RaumRepository;
import com.terminplanung.databaseClasses.StudentRepository;
import com.terminplanung.databaseClasses.Termin;
import com.terminplanung.databaseClasses.TerminRepository;
import com.terminplanung.exceptions.TerminNotFoundException;

@RestController
@RequestMapping("/terminplan")
public class TerminplanController {

    @Autowired
    private TerminRepository terminRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LehrpersonRepository lehrpersonRepository;

    @Autowired
    private RaumRepository raumRepository;

    @Autowired
    private LehrveranstaltungRepository lehrveranstaltungRepository;

    @Autowired
    private BesuchenRepository besuchenRepository;

    @Autowired
    private BenachrichtigungRepository benachrichtigungRepository;


    // --- REST METHODS ---

    // POST LOGIN
    @SuppressWarnings("null")
    @PostMapping("/login")
    public HttpStatus validateLogin(@RequestBody String requestBody) {
        //TODO: implement validation logic

        return HttpStatus.OK;
    }


    // GET CALENDAR
    @GetMapping("/fetch")
    public ResponseEntity<List<Lehrveranstaltung>> find(@PathVariable Integer id) {
        Lehrperson lehrperson = lehrpersonRepository.findById(id).get();

        List<Lehrveranstaltung> lehrveranstaltungsList = lehrveranstaltungRepository.findByLehrpersonId(id); 
        
        return new ResponseEntity<>(lehrveranstaltungsList, HttpStatus.OK);
    }


    // PUT AUSFALL MELDEN
    @PutMapping("/notify")
    public ResponseEntity<Lehrperson> putAusfall(@PathVariable Integer id) {
        
        return null;
    }


    // // Falls noch Zeit: GET VERFÜGBARKEIT

    //return new ResponseEntity<Termin>(terminRepository.findById(id).orElseThrow(() -> new TerminNotFoundException(id)), HttpStatus.NOT_FOUND);
}