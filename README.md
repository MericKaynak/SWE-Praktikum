# SWE-Praktikum

PostgreSQL DB & Adminer im Docker, start im ``Codebase/terminplanungsassistent`` Verzeichnis mit ``docker compose up``. Die jeweiligen Interfaces sind erreichbar über ``localhost:xxxx`` wobei xxxx die Port Nummer ist. WARNUNG: Möglichst nicht direkt auf der PSQL DB arbeiten, sondern über Flyway und Adminer !!!

## Die DB und Flyway
Im terminplanungsassitent, unter ``src/main/resources/db/migration`` kann ein neues SQL Skript erstellt werden. Es muss einen Namen nach dem Schema: ``VX_Name.sql`` bekommen, beginnend mit V1_, dann V2_, usw. Der Rest des Namens kann frei gewählt werden, sprich z.B. ``V1_Create_Tables.sql``. Diese Skripte werden sequenziell von Flyway beim Neustart des Programms ausgeführt um die DB auf einen gewünschten Stand zu bringen.

## Postgres DB
- Port: 5432
### Login Data
- User: swe
- PW: swe24
- DB Name: postgres

## Adminer
- Port: 8091
### Login Data
- dropdown: PostgreSQL
- server: postgres
- user: swe
- PW: swe24
- db: postgres
