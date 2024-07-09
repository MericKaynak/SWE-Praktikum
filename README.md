# SWE-Praktikum

PostgreSQL DB & Adminer im Docker, start im `Codebase/terminplanungsassistent` Verzeichnis mit `docker compose up`. Die jeweiligen Interfaces sind erreichbar über `localhost:xxxx` wobei xxxx die Port Nummer ist. WARNUNG: Möglichst nicht direkt auf der PSQL DB arbeiten, sondern über Flyway und Adminer !!!

## Adressen für REST Methoden - Port: 8080

```
BASE ADDRESS
http://localhost:8080/terminplan

ResponseEntity<String> - /unittest
@RequestBody Lehrperson

HttpStatus - /login
@RequestBody String


ResponseEntity<Lehrperson> - /createLehrperson
@RequestBody Lehrperson

// Populate the Database with necessary data for a Semester
HttpStatus - /create
 -

// Fetch all existing Lehrpersonen
ResponseEntity<List<Lehrperson>> - /fetchAllLp
 -

// Fetch all Lehrplantermine for given Lehrperson
ResponseEntity<Lehrperson> - /fetchlp/{id}
@PathVariable Integer

// Fetch all Lehrplantermine for given Student
ResponseEntity<List<Lehrplantermin>> - /fetchstudent/{id}
@PathVariable Integer

// Krankmeldung for Lehrperson
ResponseEntity<Lehrperson> - /notify
@PathVariable Integer

LoginModal - POST LOGIN
http://localhost:8080/terminplan/login

Terminplan - GET CALENDAR DATA FOR LEHRPERSON
http://localhost:8080/terminplan/fetch/{id}

Lehrperson - GET LEHRPERSON BY ID
http://localhost:8080/terminplan/fetchLp/{id}

Lehrperson - DELETE LEHRPERSON BY ID
http://localhost:8080/terminplan/delete/{id}

Lehrperson - POST LEHRPERSON BY ID
http://localhost:8080/terminplan/add/{id}

Lehrpersonen - GET ALL LEHRPERSONEN
http://localhost:8080/terminplan/fetchAllLp

Ausfall Melden - PUT AUSFALL
http://localhost:8080/terminplan/notify/{id}

ggf. noch Terminplan Generierung auslösen - POST Erstelle
http://localhost:8080/terminplan/create
```

## Postgres DB - Port: 8090

LoginModal Data

- User: swe
- PW: swe24
- DB Name: postgres

## Adminer - Port: 8091

LoginModal Data

- dropdown: PostgreSQL
- server: postgres
- user: swe
- PW: swe24
- db: postgres

## Die DB und Flyway

Im terminplanungsassitent, unter `src/main/resources/db/migration` kann ein neues SQL Skript erstellt werden. Es muss einen Namen nach dem Schema: `VX__Name.sql` (doppelter Unterstrich) bekommen, beginnend mit V1**, dann V2**, usw. Der Rest des Namens kann frei gewählt werden, sprich z.B. `V1_Create_Tables.sql`. Diese Skripte werden sequenziell von Flyway beim Neustart des Programms ausgeführt um die DB auf einen gewünschten Stand zu bringen.
