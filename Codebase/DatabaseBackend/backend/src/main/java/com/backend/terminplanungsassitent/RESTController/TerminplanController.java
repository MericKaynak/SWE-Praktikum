package com.backend.terminplanungsassitent.RESTController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import javax.sql.DataSource;

import org.json.JSONObject;
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

    private static int dauer = 2;

    private static int days = 7;

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
        JSONObject jsonObject = new JSONObject(requestBody);
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        List<Student> studentList = studentRepository.findAll();
        System.out.println(email + " " + password);

        for (Student v : studentList) {
            if (v.getEmail().equals(email) && v.getPasswort().equals(password)) {
                System.out.println(v.getEmail() + " " + v.getPasswort());
                return HttpStatus.OK;
            }
        }
        return HttpStatus.BAD_REQUEST;
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
                    while (!lehrperson.istVerfuegbar(dauer)) {
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
                        lehrperson.setWochenarbeitsstunden(lehrperson.getWochenarbeitsstunden() + dauer);
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
        List<Lehrveranstaltung> lvList = lehrveranstaltungRepository.findAll();
        LocalDate today = LocalDate.now();
        int j = 0;

        lehrplanterminRepository.deleteAll();

        System.out.println("Erstelle die spezifischen Termine basierend auf einem Startdatum und Enddatum");

        for (int i = -days; i < days * 2; i++) {
            for (Lehrveranstaltung lehrveranstaltung : lvList) {
                System.out.println(lehrveranstaltung.getDayOfWeek());
                System.out.println(today.plusDays(i).getDayOfWeek());
                if (today.plusDays(i).getDayOfWeek() == lehrveranstaltung.getDayOfWeek()) {
                    lehrplantermin.setId(j++);
                    lehrplantermin.setDatum(LocalDate.now().plusDays(i));
                    lehrplantermin.setLehrveranstaltung(lehrveranstaltung);
                    lehrplanterminRepository.save(lehrplantermin);
                }
            }
        }

        System.out.println("Erstellen erfolgreich");
    }

    // GET LIST OF ALL LEHRPERSONEN
    @GetMapping("/fetchAllLp")
    public ResponseEntity<List<Lehrperson>> fetchAllLehrpersonen() throws LehrpersonNotFoundException {
        List<Lehrperson> lehrpersonList = lehrpersonRepository.findAll();

        return new ResponseEntity<>(lehrpersonList, HttpStatus.OK);
    }

    // GET LEHRPERSON BY ID
    @GetMapping("/fetchlp/{id}")
    public ResponseEntity<List<Lehrplantermin>> findLP(@PathVariable Integer id) throws LehrpersonNotFoundException {

        List<Lehrplantermin> lehrplanterminlist = lehrplanterminRepository.findLehrplantermineByLehrpersonId(id);
        List<Lehrplantermin> lehrplanterminlistvertretet = lehrplanterminRepository.findVertretendeLehrplantermineByLehrpersonID(id);

        lehrplanterminlist.addAll(lehrplanterminlistvertretet);

        return new ResponseEntity<>(lehrplanterminlist, HttpStatus.OK);
    }

    private void fillBesuchen(){
        List<Lehrveranstaltung> lehrveranstaltungList = lehrveranstaltungRepository.findAll();
        List<Student> studentList = studentRepository.findAll();
        //List<Besuchen> besuchenList = besuchenRepository.findAll();

        for(Student student : studentList){
            for(Lehrveranstaltung lehrveranstaltung : lehrveranstaltungList){
                if(student.getStudiengang() == lehrveranstaltung.getFachbereich()){
                    Besuchen besuchen = new Besuchen();
                    besuchen.setLehrveranstaltung(lehrveranstaltung);
                    besuchen.setStudent(student);
                    besuchenRepository.save(besuchen);
                }
            }
        }
    }

    // GET CALENDAR DATA FOR LEHRPERSON
    @GetMapping("/fetch/{id}")
    public ResponseEntity<List<Lehrveranstaltung>> find(@PathVariable Integer id)
            throws LehrveranstaltungNotFoundException {
        List<Lehrveranstaltung> lehrveranstaltungsList = lehrveranstaltungRepository.findByLehrpersonId(id);

        return new ResponseEntity<>(lehrveranstaltungsList, HttpStatus.OK);
    }

    @PutMapping("/notify/{id}")
    public HttpStatus Vertretung(@PathVariable Integer id,
            @RequestBody List<LocalDate> datumList) throws LehrpersonNotFoundException {

        // create variables
        List<Lehrplantermin> lehrplanterminList = new ArrayList<>();
        LocalDate startOfPeriod = datumList.get(0);
        LocalDate endOfPeriod = datumList.get(1);

        // create Vertretung object with above LVs
        Vertretung vertretung = null;
        List<Lehrperson> lehrpersonList = lehrpersonRepository.findAll();

        // get a list of all affected LPTs
        lehrplanterminList = lehrplanterminRepository
                .findAllByDatumBetweenAndLehrpersonId(startOfPeriod, endOfPeriod,
                        id);

        List<Vertretung> newVertretungen = new ArrayList<>();

        System.out.println(lehrplanterminList.toString());

        System.out.println("ok before for");
        // find verfuegbare Lehrperson and assign them to Vertretung
        for (Lehrplantermin lehrplantermin : lehrplanterminList) {
            vertretung = new Vertretung();
            vertretung.setDatum(lehrplantermin.getDatum());
            vertretung.setLehrveranstaltung(lehrplantermin.getLehrveranstaltung());
            for (Lehrperson lp : lehrpersonList) {
                System.out.println("searching for Lehrperson");
                if (lp.istVerfuegbar(dauer) && conditionChecks(lehrplantermin.getLehrveranstaltung(), lp)) {

                    System.out.println("Lehrperson found");
                    // save Vertretungs objects
                    vertretung.setLehrperson(lp);
                    // lp.setWochenarbeitsstunden(lp.getWochenarbeitsstunden() + dauer);
                    // lehrpersonRepository.save(lp);
                    vertretungRepository.save(vertretung);
                    lehrplantermin.setVertretung(vertretung);
                    lehrplanterminRepository.save(lehrplantermin);
                    newVertretungen.add(vertretung);
                    System.out.println("vertretung saved");
                    newVertretungen.add(vertretung);
                    break;
                }
            }
            lehrpersonList.add(lehrpersonList.get(0));
            lehrpersonList.remove(0);
        }

        // notify affected students -> Call method
        for (Vertretung vertreter : newVertretungen) {
            List<String> mailListe = lehrplanterminRepository
                    .findStudentEmailsByVertretungId(vertreter.getId());
            for (String email : mailListe) {
                System.out.println("Email wurde an " + email + " gesendet!");
            }
        }

        return HttpStatus.OK;
    }
}
