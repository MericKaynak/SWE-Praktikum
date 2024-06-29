package com.backend.terminplanungsassitent.terminplanung;

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

import com.backend.terminplanungsassitent.databaseClasses.BenachrichtigungRepository;
import com.backend.terminplanungsassitent.databaseClasses.BesuchenRepository;
import com.backend.terminplanungsassitent.databaseClasses.Lehrperson;
import com.backend.terminplanungsassitent.databaseClasses.LehrpersonRepository;
import com.backend.terminplanungsassitent.databaseClasses.Lehrveranstaltung;
import com.backend.terminplanungsassitent.databaseClasses.LehrveranstaltungRepository;
import com.backend.terminplanungsassitent.databaseClasses.RaumRepository;
import com.backend.terminplanungsassitent.databaseClasses.StudentRepository;
import com.backend.terminplanungsassitent.databaseClasses.TerminRepository;

import com.backend.terminplanungsassitent.exceptions.LehrpersonNotFoundException;
import com.backend.terminplanungsassitent.exceptions.LehrveranstaltungNotFoundException;

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

    // POST LERHPERSONZUTEILUNG
    /**
     * Maps Lehrpersonen to Lehrveranstaltung, taking into accout the working hour limit for Lehrperson for each week.
     * 
     * @return ResponseEntity containing all Lehrveranstaltung as JSON
     * @throws LehrpersonNotFoundException
     * @throws LehrveranstaltungNotFoundException
     */
    @GetMapping("/createmapping")
    public ResponseEntity<List<Lehrveranstaltung>> createMapping() throws LehrpersonNotFoundException, LehrveranstaltungNotFoundException {
        List<Lehrveranstaltung> lehrveranstaltungList = lehrveranstaltungRepository.findAll();
        List<Lehrperson> lehrpersonList = lehrpersonRepository.findAll();

        int lehrpersonIndex = 0;

        Lehrperson lehrperson = lehrpersonList.get(lehrpersonIndex);

        for (Lehrveranstaltung lehrveranstaltung : lehrveranstaltungList) {
        
            // ensure only LVs with no LP are updated 
            if(lehrveranstaltung.getLehrperson() == null) {

                // check if Lehrperson can be assigned
                while (lehrperson.getWochenarbeitsstunden() >= 18) {
                    if(++lehrpersonIndex >= lehrpersonList.size()) {
                        throw new LehrpersonNotFoundException((long) lehrpersonIndex);
                    }
                    lehrperson = lehrpersonList.get(lehrpersonIndex);
                }

                lehrveranstaltung.setLehrperson(lehrperson);
                lehrperson.setWochenarbeitsstunden(lehrperson.getWochenarbeitsstunden() + 2);
                lehrveranstaltungRepository.save(lehrveranstaltung);
            }
        }

        return new ResponseEntity<>(lehrveranstaltungList, HttpStatus.OK);
    }


    // READ LIST OF ALL LEHRPERSON
    @GetMapping("/fetchAllLp")
    public ResponseEntity<List<Lehrperson>> fetchAllLehrpersonen() throws LehrpersonNotFoundException {
        List<Lehrperson> lehrpersonList = lehrpersonRepository.findAll();

        return new ResponseEntity<>(lehrpersonList, HttpStatus.OK);
    }

    @GetMapping("/fetchlp/{id}")
    public ResponseEntity<Lehrperson> findLP(@PathVariable Long id) throws LehrpersonNotFoundException {
        return new ResponseEntity<Lehrperson>(lehrpersonRepository.findById(id)
        .orElseThrow(() -> new LehrpersonNotFoundException(id)), HttpStatus.OK);
    }

    // GET CALENDAR
    @GetMapping("/fetch/{id}")
    public ResponseEntity<List<Lehrveranstaltung>> find(@PathVariable Long id) throws LehrveranstaltungNotFoundException {
        List<Lehrveranstaltung> lehrveranstaltungsList = lehrveranstaltungRepository.findByLehrpersonId(id); 
        
        return new ResponseEntity<>(lehrveranstaltungsList, HttpStatus.OK);
    }


    // PUT AUSFALL MELDEN
    @PutMapping("/notify")
    public ResponseEntity<Lehrperson> putAusfall(@PathVariable Long id) {
        //TODO: Ausfall Logik implementieren
        return null;
    }
}