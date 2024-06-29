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
import com.backend.terminplanungsassitent.databaseClasses.Raum;
import com.backend.terminplanungsassitent.databaseClasses.RaumRepository;
import com.backend.terminplanungsassitent.databaseClasses.StudentRepository;
import com.backend.terminplanungsassitent.databaseClasses.Termin;
import com.backend.terminplanungsassitent.databaseClasses.TerminRepository;

import com.backend.terminplanungsassitent.exceptions.LehrpersonNotFoundException;
import com.backend.terminplanungsassitent.exceptions.LehrveranstaltungNotFoundException;

import com.backend.terminplanungsassitent.terminplanung.TimeComparison;

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
        // TODO: implement validation logic

        return HttpStatus.OK;
    }

    /*
     * // POST LEHRPERSONZUTEILUNG
     * /
     * Maps Lehrpersonen to Lehrveranstaltung, taking into account the per week
     * working hour limit for Lehrperson.
     * 
     * @return ResponseEntity containing all Lehrveranstaltung as JSON
     * 
     * @throws LehrpersonNotFoundException
     * 
     * @throws LehrveranstaltungNotFoundException
     *
     * @GetMapping("/createmapping")
     * public ResponseEntity<List<Lehrveranstaltung>> createMapping() throws
     * LehrpersonNotFoundException, LehrveranstaltungNotFoundException {
     * List<Lehrveranstaltung> lehrveranstaltungList =
     * lehrveranstaltungRepository.findAll();
     * List<Lehrperson> lehrpersonList = lehrpersonRepository.findAll();
     * 
     * int lehrpersonIndex = 0;
     * int i = 0;
     * 
     * Lehrperson lehrperson = lehrpersonList.get(lehrpersonIndex);
     * 
     * for (Lehrveranstaltung lehrveranstaltung : lehrveranstaltungList) {
     * 
     * // ensure only LVs with no LP are updated
     * if(lehrveranstaltung.getLehrperson() == null) {
     * 
     * // check if Lehrperson can be assigned
     * while (lehrperson.istVerfuegbar()) {
     * if(++lehrpersonIndex >= lehrpersonList.size()) {
     * throw new LehrpersonNotFoundException((long) lehrpersonIndex);
     * }
     * lehrpersonRepository.save(lehrperson);
     * lehrperson = lehrpersonList.get(lehrpersonIndex);
     * }
     * 
     * List<Lehrveranstaltung> lehrpersonLVList =
     * lehrveranstaltungRepository.findByLehrpersonId(lehrperson.getId());
     * 
     * // check if Lehrveranstaltung Termin is in same timeslot as other
     * Lehrveranstaltung for this Lehrperson
     * for (Lehrveranstaltung lehrpersonLV : lehrpersonLVList) {
     * if (lehrpersonLV.getTermin().getDatum() ==
     * lehrveranstaltung.getTermin().getDatum()
     * && lehrpersonLV.getTermin().getZeitraumStart() ==
     * lehrveranstaltung.getTermin().getZeitraumStart()) {
     * // solve conflict for same timeslot
     * while (lehrpersonIndex < lehrpersonList.size() &&
     * !lehrpersonList.get(lehrpersonIndex + i).istVerfuegbar()) {
     * i++;
     * }
     * lehrveranstaltung.setLehrperson(lehrpersonList.get(lehrpersonIndex + i));
     * i = 1;
     * }
     * }
     * 
     * // check if Lehrveranstaltung Termin is in different locations
     * for (Lehrveranstaltung lehrpersonLV : lehrpersonLVList) {
     * if(lehrpersonLV.getRaum().getStandort() !=
     * lehrveranstaltung.getRaum().getStandort()) {
     * if(lehrpersonLV.getTermin().getDatum() ==
     * lehrveranstaltung.getTermin().getDatum()
     * && (TimeComparison.areTimesWithinTwoHours(lehrpersonLV.getTermin().
     * getZeitraumEnd(), lehrveranstaltung.getTermin().getZeitraumStart())
     * || TimeComparison.areTimesWithinTwoHours(lehrpersonLV.getTermin().
     * getZeitraumStart(), lehrveranstaltung.getTermin().getZeitraumEnd()))
     * ) {
     * // solve conflict for being within 2 hours
     * }
     * 
     * }
     * }
     * 
     * 
     * // assign Lehrperson to Lehrveranstaltung & update Wochenarbeitsstunden
     * lehrveranstaltung.setLehrperson(lehrperson);
     * lehrveranstaltungRepository.save(lehrveranstaltung);
     * lehrperson.setWochenarbeitsstunden(lehrperson.getWochenarbeitsstunden() + 2);
     * }
     * }
     * 
     * return new ResponseEntity<>(lehrveranstaltungList, HttpStatus.OK);
     * }
     */

    @GetMapping("/createmapping")
    public ResponseEntity<List<Lehrveranstaltung>> createMapping()
            throws LehrpersonNotFoundException, LehrveranstaltungNotFoundException {
        List<Lehrperson> lehrpersonList = lehrpersonRepository.findAll();
        List<Lehrveranstaltung> lehrveranstaltungList = lehrveranstaltungRepository.findAll();
        List<Termin> terminList = terminRepository.findAll();
        List<Raum> raumList = raumRepository.findAll();

        int lehrpersonIndex = 0;
        int terminIndex = 0;
        int raumIndex = 0;

        // assign each Lehrveranstaltung a Termin
        for (Lehrveranstaltung lehrveranstaltung : lehrveranstaltungList) {
            lehrveranstaltung.setTermin(terminList.get(terminIndex++));
            terminIndex %= terminList.size();
        }

        // assign each Lehrveranstaltung a room
        for (Lehrveranstaltung lehrveranstaltung : lehrveranstaltungList) {
            lehrveranstaltung.setRaum(raumList.get(raumIndex++));
            raumIndex %= raumList.size();
        }

        lehrveranstaltungList = lehrveranstaltungRepository.findLehrveranstaltungWithoutLehrperson();
        if (lehrveranstaltungList == null) {
            throw new LehrveranstaltungNotFoundException(null);
        }

        // assign Lehrpersonen to Lehrveranstaltung
        for (Lehrveranstaltung lehrveranstaltung : lehrveranstaltungList) {
            Lehrperson lehrperson = lehrpersonList.get(lehrpersonIndex);

            // check if Lehrperson can be assigned
            while (lehrperson.istVerfuegbar()) {
                if (++lehrpersonIndex >= lehrpersonList.size()) {
                    throw new LehrpersonNotFoundException((long) lehrpersonIndex);
                }
                lehrpersonRepository.save(lehrperson);
                lehrperson = lehrpersonList.get(lehrpersonIndex);
            }

            // check if Lehrperson is same as Lehrperson for LV with same timeslot
            List<Lehrveranstaltung> checkForSameTimeSlot = lehrveranstaltungRepository
                    .findByTerminAndExcludeCurrent(lehrveranstaltung.getTermin(), lehrveranstaltung.getId());
            if (checkForSameTimeSlot != null) {
                for (Lehrveranstaltung otherLehrveranstaltung : checkForSameTimeSlot) {
                    if (lehrveranstaltung.checkSameLehrperson(otherLehrveranstaltung)) {
                        // handle same timeslot booking case
                    }
                }
            }

            // check if there would be travel time conflicts for the Lehrperson
            List<Lehrveranstaltung> checkForDifferentStandort = lehrveranstaltungRepository
                    .findByLehrpersonId(lehrperson.getId());
            if (checkForDifferentStandort != null) {
                for (Lehrveranstaltung otherLehrveranstaltung : checkForDifferentStandort) {
                    if (lehrveranstaltung.checkTravelTimeConflict(otherLehrveranstaltung)) {
                        // handle travel time conflict
                    }
                }
            }

            // assign Lehrperson to Lehrveranstaltung
            lehrveranstaltung.setLehrperson(lehrperson);
            lehrveranstaltungRepository.save(lehrveranstaltung);
            lehrperson.setWochenarbeitsstunden(lehrperson.getWochenarbeitsstunden() + 2);
        }

        for (Lehrveranstaltung lehrveranstaltung : lehrveranstaltungList) {
            lehrveranstaltungRepository.save(lehrveranstaltung);z
        }

        return null;
    }

    // GET LIST OF ALL LEHRPERSONEN
    @GetMapping("/fetchAllLp")
    public ResponseEntity<List<Lehrperson>> fetchAllLehrpersonen() throws LehrpersonNotFoundException {
        List<Lehrperson> lehrpersonList = lehrpersonRepository.findAll();

        return new ResponseEntity<>(lehrpersonList, HttpStatus.OK);
    }

    // GET LEHRPERSON BY ID
    @GetMapping("/fetchlp/{id}")
    public ResponseEntity<Lehrperson> findLP(@PathVariable Long id) throws LehrpersonNotFoundException {
        return new ResponseEntity<Lehrperson>(lehrpersonRepository.findById(id)
                .orElseThrow(() -> new LehrpersonNotFoundException(id)), HttpStatus.OK);
    }

    // GET CALENDAR DATA FOR LEHRPERSON
    @GetMapping("/fetch/{id}")
    public ResponseEntity<List<Lehrveranstaltung>> find(@PathVariable Integer id)
            throws LehrveranstaltungNotFoundException {
        List<Lehrveranstaltung> lehrveranstaltungsList = lehrveranstaltungRepository.findByLehrpersonId(id);

        return new ResponseEntity<>(lehrveranstaltungsList, HttpStatus.OK);
    }

    // PUT AUSFALL MELDEN
    @PutMapping("/notify")
    public ResponseEntity<Lehrperson> putAusfall(@PathVariable Long id) {
        // TODO: Ausfall Logik implementieren
        return null;
    }
}