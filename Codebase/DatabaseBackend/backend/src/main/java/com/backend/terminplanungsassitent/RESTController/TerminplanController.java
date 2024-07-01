package com.backend.terminplanungsassitent.RESTController;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
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

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/terminplan")
public class TerminplanController {

    @Autowired
    private DataSource dataSource;

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

    /**
     * TEST FUNCTION ONLY - Resets the database to a clean slate. Database must be
     * cleaned or else a Flyway checksum error fails next start of Application.
     */
    @PostMapping("/reset")
    @PostConstruct
    public void executeSqlFiles() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(
                new ClassPathResource(
                        "db\\V2_Deployment.sql"));
        resourceDatabasePopulator.addScript(
                new ClassPathResource(
                        "db\\migration\\V2__Insert_Data.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }

    @PostMapping("/unittest")
    public ResponseEntity<String> lehrpersonUnitTest(@RequestBody Lehrperson lehrperson)
            throws LehrpersonNotFoundException {
        boolean success = true;
        String result = "Test failed.";
        Long originalCount = lehrpersonRepository.count();

        try {
            lehrpersonRepository.save(lehrperson);
        } catch (RuntimeException e) {
            success = false;
            e.printStackTrace();
            result += "\n" + e.getStackTrace();
        }

        System.out.println("insert " + success);

        try {
            String original = lehrperson.toString();
            String testObject = lehrpersonRepository.findById(lehrperson.getId()).get().toString();

            if (!original.equals(testObject)) {
                success = false;
            }
            System.out.println("find person" + success);
            System.out.println(original);
            System.out.println(testObject);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            result += "\n" + e.getStackTrace();
        } catch (LehrpersonNotFoundException e) {
            e.printStackTrace();
            result += "\n" + e.getStackTrace();
        }

        try {
            Long newCount = lehrpersonRepository.count();
            System.out.println(newCount + " " + originalCount);
            lehrpersonRepository.deleteById(lehrperson.getId());
            if (originalCount == newCount) {
                success = false;
            }
            System.out.println("delete " + success);
        } catch (LehrpersonNotFoundException e) {
            e.printStackTrace();
            result += "\n" + e.getStackTrace();
        }
        if (success) {
            result = "Test succeeded.";
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Handles login requests.
     * 
     * @param requestBody
     * @return
     */
    // POST LOGIN
    @SuppressWarnings("null")
    @PostMapping("/login")
    public HttpStatus validateLogin(@RequestBody String requestBody) {
        // TODO: implement validation logic

        return HttpStatus.OK;
    }

    /**
     * Creates a Lehrperson in the database.
     * 
     * @param lehrperson
     * @return
     */
    // POST CREATE LEHRPERSON
    @SuppressWarnings("null")
    @PostMapping("/createLehrperson")
    public ResponseEntity<Lehrperson> createLehrperson(@RequestBody Lehrperson lehrperson) {
        lehrpersonRepository.save(lehrperson);
        return new ResponseEntity<>(lehrperson, HttpStatus.OK);
    }

    /**
     * Creates the mapping of Termin, Raum, and Lehrperson to Lehrveranstaltung, in
     * that order.
     * 
     * @return
     * @throws LehrpersonNotFoundException
     * @throws LehrveranstaltungNotFoundException
     */
    @GetMapping("/createmapping")
    public ResponseEntity<List<Lehrveranstaltung>> createMapping()
            throws LehrpersonNotFoundException, LehrveranstaltungNotFoundException {
        List<Lehrveranstaltung> lehrveranstaltungList = null;
        try {
            lehrveranstaltungList = lehrveranstaltungRepository.findAll();
            if (lehrveranstaltungList == null || lehrveranstaltungList.isEmpty()) {
                throw new LehrveranstaltungNotFoundException(null);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        lehrveranstaltungList = assignTermine(lehrveranstaltungList);
        lehrveranstaltungRepository.saveAll(lehrveranstaltungList);
        System.out.println("Termine assigned");

        lehrveranstaltungList = assignRaeume(lehrveranstaltungList);
        lehrveranstaltungRepository.saveAll(lehrveranstaltungList);
        System.out.println("Räume assigned");

        assignLehrpersonen(lehrveranstaltungList);
        System.out.println("Lehrpersonen assigned");

        return null;
    }

    /**
     * Assigns a Termin to every Lehrveranstaltung.
     * 
     * @param lehrveranstaltungList
     * @return
     */
    private List<Lehrveranstaltung> assignTermine(List<Lehrveranstaltung> lehrveranstaltungList) {
        List<Termin> terminList = terminRepository.findAll();
        int terminIndex = 0;

        // assign each Lehrveranstaltung a Termin
        for (Lehrveranstaltung lehrveranstaltung : lehrveranstaltungList) {
            lehrveranstaltung.setTermin(terminList.get(terminIndex++));
            terminIndex %= terminList.size();
        }

        return lehrveranstaltungList;
    }

    /**
     * Assigns a Raum to every Lehrveranstaltung.
     * 
     * @param lehrveranstaltungList
     * @return
     */
    private List<Lehrveranstaltung> assignRaeume(List<Lehrveranstaltung> lehrveranstaltungList) {
        List<Raum> raumList = raumRepository.findAll();
        int raumIndex = 0;

        // assign each Lehrveranstaltung a room
        for (Lehrveranstaltung lehrveranstaltung : lehrveranstaltungList) {
            lehrveranstaltung.setRaum(raumList.get(raumIndex++));
            raumIndex %= raumList.size();
        }

        return lehrveranstaltungList;
    }

    /**
     * Assigns the pool of Lehrpersonen to Lehrveranstaltung, checking for time and
     * travel conflicts while making sure no Lehrperson receives more than 18
     * Wochenstunden.
     * Throws an exception if the list of Lehrveranstaltungen is empty.
     * Throws an exception if there aren't enough Lehrpersonen to assign one to
     * every Lehrveranstaltung.
     * 
     * @param lehrveranstaltungList
     * @throws LehrveranstaltungNotFoundException, LehrpersonNotFoundException
     */
    private void assignLehrpersonen(List<Lehrveranstaltung> lehrveranstaltungList)
            throws LehrveranstaltungNotFoundException, LehrpersonNotFoundException {
        List<Lehrperson> lehrpersonList = lehrpersonRepository.findAll();
        List<Lehrveranstaltung> elementsToRemove = new ArrayList<>();

        int lehrpersonIndex = 0;

        if (lehrveranstaltungList == null) {
            throw new LehrveranstaltungNotFoundException(null);
        }

        try {
            while (!lehrveranstaltungList.isEmpty()) {
                // assign Lehrpersonen to Lehrveranstaltung
                for (Lehrveranstaltung lehrveranstaltung : lehrveranstaltungList) {
                    Lehrperson lehrperson = lehrpersonList.get(lehrpersonIndex);

                    // check if Lehrperson can be assigned & get next if not
                    while (!lehrperson.istVerfuegbar()) {
                        if (++lehrpersonIndex >= lehrpersonList.size()) {
                            throw new LehrpersonNotFoundException(lehrpersonIndex);
                        }
                        lehrpersonRepository.save(lehrperson);
                        lehrperson = lehrpersonList.get(lehrpersonIndex);
                    }

                    if (conditionChecks(lehrveranstaltung, lehrperson)) {
                        // assign Lehrperson to lehrveranstaltung and save change
                        lehrveranstaltung.setLehrperson(lehrperson);
                        lehrveranstaltungRepository.save(lehrveranstaltung);
                        elementsToRemove.add(lehrveranstaltung);
                        lehrperson.setWochenarbeitsstunden(lehrperson.getWochenarbeitsstunden() + 2);
                    }
                }
                // remove elements which were successfully processed
                // leave behind only unsuccessful elements
                lehrveranstaltungList.removeAll(elementsToRemove);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks for two conditions: 1. would the Lehrperson be assigned to two
     * Lehrveranstaltung happening at the same time?
     * 2. Does the Lehrperson have 2 hours to travel to a new location if the
     * Standort is in a different?
     * 
     * @param lehrveranstaltung
     * @param lehrperson
     * @return false if either condition is fails
     */
    private boolean conditionChecks(Lehrveranstaltung lehrveranstaltung, Lehrperson lehrperson) {

        // check if Lehrperson is same as Lehrperson for LV with same timeslot
        List<Lehrveranstaltung> overlapCheckList = lehrveranstaltungRepository
                .findByTerminWochentagAndTerminZeitraumStart(lehrveranstaltung.getTermin().getWochentag(),
                        lehrveranstaltung.getTermin().getZeitraumStart());

        if (overlapCheckList != null) {
            for (Lehrveranstaltung otherLehrveranstaltung : overlapCheckList) {
                if (lehrveranstaltung.checkSameLehrperson(otherLehrveranstaltung, lehrperson)) {
                    return false;
                }
            }
        }

        // check if there would be travel time conflicts for the Lehrperson
        overlapCheckList = lehrveranstaltungRepository
                .findByLehrpersonId(lehrperson.getId());
        if (overlapCheckList != null) {
            for (Lehrveranstaltung otherLehrveranstaltung : overlapCheckList) {
                if (lehrveranstaltung.checkTravelTimeConflict(otherLehrveranstaltung)) {
                    return false;
                }
            }
        }

        return true;
    }

    // GET LIST OF ALL LEHRPERSONEN
    @GetMapping("/fetchAllLp")
    public ResponseEntity<List<Lehrperson>> fetchAllLehrpersonen() throws LehrpersonNotFoundException {
        List<Lehrperson> lehrpersonList = lehrpersonRepository.findAll();

        return new ResponseEntity<>(lehrpersonList, HttpStatus.OK);
    }

    // GET LEHRPERSON BY ID
    @GetMapping("/fetchlp/{id}")
    public ResponseEntity<Lehrperson> findLP(@PathVariable Integer id) throws LehrpersonNotFoundException {
        return new ResponseEntity<>(lehrpersonRepository.findById(id)
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