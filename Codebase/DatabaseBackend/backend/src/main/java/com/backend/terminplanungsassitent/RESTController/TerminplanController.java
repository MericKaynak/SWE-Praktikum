package com.backend.terminplanungsassitent.RESTController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.terminplanungsassitent.databaseClasses.BenachrichtigungRepository;
import com.backend.terminplanungsassitent.databaseClasses.Besuchen;
import com.backend.terminplanungsassitent.databaseClasses.BesuchenRepository;
import com.backend.terminplanungsassitent.databaseClasses.Lehrperson;
import com.backend.terminplanungsassitent.databaseClasses.LehrpersonRepository;
import com.backend.terminplanungsassitent.databaseClasses.Lehrplantermin;
import com.backend.terminplanungsassitent.databaseClasses.LehrplanterminRepository;
import com.backend.terminplanungsassitent.databaseClasses.Lehrveranstaltung;
import com.backend.terminplanungsassitent.databaseClasses.LehrveranstaltungRepository;
import com.backend.terminplanungsassitent.databaseClasses.Raum;
import com.backend.terminplanungsassitent.databaseClasses.RaumRepository;
import com.backend.terminplanungsassitent.databaseClasses.StudentRepository;
import com.backend.terminplanungsassitent.databaseClasses.Student;
import com.backend.terminplanungsassitent.databaseClasses.Termin;
import com.backend.terminplanungsassitent.databaseClasses.TerminRepository;
import com.backend.terminplanungsassitent.databaseClasses.Vertretung;
import com.backend.terminplanungsassitent.databaseClasses.VertretungRepository;
import com.backend.terminplanungsassitent.databaseClasses.VerwalterRepository;
import com.backend.terminplanungsassitent.databaseClasses.Verwalter;

import com.backend.terminplanungsassitent.exceptions.LehrpersonNotFoundException;
import com.backend.terminplanungsassitent.exceptions.LehrveranstaltungNotFoundException;

@CrossOrigin
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
    private LehrplanterminRepository lehrplanterminRepository;

    @Autowired
    private LehrveranstaltungRepository lehrveranstaltungRepository;

    @Autowired
    private BesuchenRepository besuchenRepository;

    @Autowired
    private BenachrichtigungRepository benachrichtigungRepository;

    @Autowired
    private VertretungRepository vertretungRepository;

    @Autowired
    private VerwalterRepository verwalterRepository;

    // --- REST METHODS ---

    /**
     * TEST FUNCTION ONLY - Resets the database to a clean slate. Database must be
     * cleaned or else a Flyway checksum error fails next start of Application.
     */
    /*
     * @PostMapping("/reset")
     * 
     * @PostConstruct
     * public void executeSqlFiles() {
     * ResourceDatabasePopulator resourceDatabasePopulator = new
     * ResourceDatabasePopulator();
     * resourceDatabasePopulator.addScript(
     * new ClassPathResource(
     * "db\\resetDB.sql"));
     * resourceDatabasePopulator.addScript(
     * new ClassPathResource(
     * "db\\migration\\V2__Insert_Data.sql"));
     * resourceDatabasePopulator.execute(dataSource);
     * }
     */

    /**
     * Runs through several steps to test the basic CRUD (Create, Read, Update,
     * Delete) functions for the Lehrperson class and LehrpersonRepository
     * 
     * @param lehrperson
     * @return
     * @throws LehrpersonNotFoundException
     */
    @PostMapping("/unittest")
    public ResponseEntity<String> lehrpersonUnitTest(@RequestBody Lehrperson lehrperson)
            throws LehrpersonNotFoundException {
        boolean success = true;
        String result = "Test failed.";
        Long originalCount = lehrpersonRepository.count();

        System.out.println(lehrperson.toString());

        System.out.println("Testing INSERT");
        try {
            lehrpersonRepository.save(lehrperson);
        } catch (RuntimeException e) {
            success = false;
            e.printStackTrace();
            result += "\n" + e.getStackTrace();
        }
        System.out.println("INSERT " + success);

        System.out.println("Testing READ function");
        try {
            String original = lehrperson.toString();
            String testObject = lehrpersonRepository.findById(lehrperson.getId()).get().toString();

            System.out.println("original: " + original);
            System.out.println("testObject: " + testObject);

            if (!original.equals(testObject)) {
                success = false;
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            result += "\n" + e.getStackTrace();
        } catch (LehrpersonNotFoundException e) {
            e.printStackTrace();
            result += "\n" + e.getStackTrace();
        }
        System.out.println("READ " + success);

        System.out.println("Testing UPDATE function");
        try {
            lehrperson.setName("Teststring");
            lehrpersonRepository.save(lehrperson);
            String originalUpdate = lehrperson.toString();
            String testObjectUpdate = lehrpersonRepository.findById(lehrperson.getId()).get().toString();

            System.out.println("original: " + originalUpdate);
            System.out.println("testObject: " + testObjectUpdate);

            if (!originalUpdate.equals(testObjectUpdate)) {
                success = false;
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            result += "\n" + e.getStackTrace();
        } catch (LehrpersonNotFoundException e) {
            e.printStackTrace();
            result += "\n" + e.getStackTrace();
        }
        System.out.println("UPDATE " + success);

        System.out.println("Testing DELETE function");
        try {
            Long newCount = lehrpersonRepository.count();
            System.out.println("new count: " + newCount + ", old count: " + originalCount);
            lehrpersonRepository.deleteById(lehrperson.getId());
            if (originalCount == newCount) {
                success = false;
            }
        } catch (LehrpersonNotFoundException e) {
            e.printStackTrace();
            result += "\n" + e.getStackTrace();
        }
        System.out.println("DELETE " + success);

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
        /*
         * JSONObject jsonObject = new JSONObject(requestBody);
         * String email = jsonObject.getString("email");
         * String password = jsonObject.getString("password");
         * List<Verwalter> verwalterList = verwaltungRepository.findAll();
         * List<Student> studentList = studentRepository.findAll();
         * if (email.endsWidt(email.endsWith("@stud.hn.de"))) {
         * for (Student v : StudentList) {
         * if (v.getEmail() == email || v.getPassword() == password) {
         * return HttpStatus.OK;
         * }
         * }
         * return HttpStatus.OK;
         * } else if (email.endsWith("@hs-niederrhein.de")) {
         * for (Verwalter v : verwalterList) {
         * if (v.getEmail() == email || v.getPassword() == password) {
         * return HttpStatus.OK;
         * }
         * }
         * return HttpStatus.OK;
         * 
         * } else {
         * return HttpStatus.BAD_REQUEST; // Or any other appropriate status code
         * }
         * return HttpStatus.BAD_REQUEST;
         */
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
    @PostMapping("/create")
    public HttpStatus createMapping(/* @RequestBody LocalDate startDatum */)
            throws LehrpersonNotFoundException, LehrveranstaltungNotFoundException {
        List<Lehrveranstaltung> lehrveranstaltungList = new ArrayList<>();

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

        populateLehrplanterminTable();

        return HttpStatus.OK;
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

    private void populateLehrplanterminTable() {
        Lehrplantermin lehrplantermin = new Lehrplantermin();
        Lehrveranstaltung lv = lehrveranstaltungRepository.findById(1L).get();
        lehrplantermin.setId(1);
        lehrplantermin.setDatum(LocalDate.now());
        lehrplantermin.setLehrveranstaltung(lv);

        lehrplanterminRepository.save(lehrplantermin);

        System.out.println("Erstelle die spezifischen Termine basierend auf einem Startdatum und Enddatum");
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

    @GetMapping("/vertretung")
    public ResponseEntity<List<Vertretung>> Vertretung(/* @RequestParam List<LocalDate> datumList, */
            @RequestBody Lehrperson kranke_person) {

        List<Besuchen> besuchenList = new ArrayList<>();
        List<Lehrveranstaltung> lehrveranstaltungList = new ArrayList<>();
        // alle Lehrplantermine für Lehrperson kranke_person
        List<Lehrplantermin> lptKrankePerson = lehrplanterminRepository
                .findLehrplantermineByLehrpersonId(kranke_person.getId());

        /*
         * for (Lehrplantermin lehrplantermin : lvKrankePerson) {
         * System.out.println("Lehrveranstaltung: " +
         * lehrplantermin.getLehrveranstaltung().getTitel());
         * //System.out.println("Lehrperson: " +
         * lehrplantermin.getLehrveranstaltung().getLehrperson().getName());
         * //System.out.println("Raum: " +
         * lehrplantermin.getLehrveranstaltung().getRaum().getBezeichnung());
         * System.out.println("Wochentag: " +
         * lehrplantermin.getLehrveranstaltung().getTermin().getWochentag());
         * System.out.println("Zeitraum: " +
         * lehrplantermin.getLehrveranstaltung().getTermin().getZeitraumStart()
         * + " - " +
         * lehrplantermin.getLehrveranstaltung().getTermin().getZeitraumEnd());
         * System.out.println("Datum: " + lehrplantermin.getDatum());
         * }
         */

        List<Lehrperson> lehrpersonList = lehrpersonRepository.findAll();
        List<Vertretung> vertretungList = new ArrayList<>();

        while (!lptKrankePerson.isEmpty()) {
            Lehrveranstaltung lv = lptKrankePerson.get(0).getLehrveranstaltung();
            String wochentag = lptKrankePerson.get(0).getLehrveranstaltung().getTermin().getWochentag();
            LocalTime zeitraumStart = lptKrankePerson.get(0).getLehrveranstaltung().getTermin().getZeitraumStart();
            LocalTime zeitraumEnde = lptKrankePerson.get(0).getLehrveranstaltung().getTermin().getZeitraumEnd();
            LocalDate datum = lptKrankePerson.get(0).getDatum();

            // checks if a vertretung has not been found
            // if so we put "null" into Lehrperson
            boolean vertretung_Found = false;

            Vertretung vertretung = new Vertretung();
            for (Lehrperson lehrperson : lehrpersonList) {
                System.out.println("Ich bin nicht drin!");
                System.out.println(lehrperson.getId() != kranke_person.getId());
                System.out.println(lehrperson.istVerfuegbar());
                System.out.println(conditionChecks(lv, lehrperson));
                if (lehrperson.getId() != kranke_person.getId() &&
                        lehrperson.istVerfuegbar() &&
                        conditionChecks(lv, lehrperson)) {

                    System.out.println("Ich bin hier: " + lv.getTitel() + " - " + lehrperson.getName());

                    vertretung.setLehrperson(lehrperson);
                    vertretung.setDatum(datum);
                    vertretungList.add(vertretung);

                    lehrperson.setWochenarbeitsstunden(lehrperson.getWochenarbeitsstunden() + 2);
                    lehrpersonRepository.save(lehrperson);

                    vertretung_Found = true;

                    // remove the found lehrveranstaltung from the kranke_person
                    lptKrankePerson.remove(lv);

                    break;
                }
            }

            if (!vertretung_Found) {
                vertretung.setLehrperson(null);
                vertretung.setDatum(datum);

                vertretungList.add(vertretung);

                Lehrplantermin lptToReplace = lptKrankePerson.get(0);
                lptKrankePerson.remove(lptToReplace);
            }
        }

        for (Vertretung vertretung : vertretungList) {
            System.out.println(vertretung.toString());

            // besuchenList =
            // besuchenRepository.findAllByLehrveranstaltungId(vertretung.getLehrveranstaltung().getId());
            for (Besuchen besuchen : besuchenList) {
                System.out.println("Benachrichtige Student " + besuchen.getStudent().getEmail() + " wegen Änderung an "
                        + besuchen.getLehrveranstaltung().getTitel());
            }
        }
        return new ResponseEntity<>(vertretungList, HttpStatus.OK);
    }
}
