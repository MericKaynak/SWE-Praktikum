package com.terminplanung.terminplanungsassistent;

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

import com.terminplanung.databaseClasses.Lehrperson;
import com.terminplanung.databaseClasses.Termin;
import com.terminplanung.databaseClasses.TerminplanRepository;
import com.terminplanung.exceptions.TerminplanNotFoundException;

@RestController
@RequestMapping("/terminplan")
public class TerminplanController {

    @Autowired
    private TerminplanRepository terminplanRepository;


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
    public ResponseEntity<Termin> getTerminplan(@PathVariable Long id) throws TerminplanNotFoundException {
        return new ResponseEntity<Termin>(terminplanRepository.findById(id)
        .orElseThrow(() -> new TerminplanNotFoundException(id)), HttpStatus.NOT_FOUND);
    }


    // PUT AUSFALL MELDEN
    @PutMapping("/notify")
    public ResponseEntity<Lehrperson> putAusfall(@PathVariable Long id, @RequestBody Lehrperson lehrperson) {
        
        return null;
    }


    // // Falls noch Zeit: GET VERFÜGBARKEIT
}