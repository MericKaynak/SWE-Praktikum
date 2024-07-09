DROP TABLE Student, Lehrperson,Raum,Termin,Lehrveranstaltung, Besuchen,Benachrichtigung,Verwalter,Lehrplantermin,Vertretung,flyway_schema_history CASCADE;
DROP SEQUENCE vertretung_seq;


-- FÜR TESTZWECKE, NICHT ALS FLYWAY ANWEISUNG

CREATE TABLE Student (
    ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Email VARCHAR(100),
    Fachbereich VARCHAR(100)
);

CREATE TABLE Lehrperson (
    ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Email VARCHAR(100),
    Rolle VARCHAR(50),
    Wochenarbeitsstunden INT
);

CREATE TABLE Raum (
    ID INT PRIMARY KEY,
    Bezeichnung VARCHAR(100),
    Kapazitaet INT,
    Standort VARCHAR(100)
);

CREATE TABLE Termin(
    ID INT PRIMARY KEY,
    Wochentag VARCHAR(100),
    Zeitraum_Start TIME,
    Zeitraum_End TIME,
    CONSTRAINT Unique_Termin UNIQUE (Wochentag, Zeitraum_Start, Zeitraum_End)
);

CREATE TABLE Lehrveranstaltung (
    ID INT PRIMARY KEY,
    Titel VARCHAR(100),
    Fachbereich VARCHAR(100),
    Dauer INT,
    Raum_ID INT,
    Termin_ID INT,
    Lehrperson_ID INT,
    FOREIGN KEY (Raum_ID) REFERENCES Raum(ID),
    FOREIGN KEY (Termin_ID) REFERENCES Termin(ID),
    FOREIGN KEY (Lehrperson_ID) REFERENCES Lehrperson(ID)
);

CREATE TABLE Besuchen (
    Student_ID INT,
    Lehrveranstaltung_ID INT,
    PRIMARY KEY (Student_ID, Lehrveranstaltung_ID),
    FOREIGN KEY (Student_ID) REFERENCES Student(ID),
    FOREIGN KEY (Lehrveranstaltung_ID) REFERENCES Lehrveranstaltung(ID)
);

CREATE TABLE Benachrichtigung (
    ID INT PRIMARY KEY,
    Nachrichtentyp VARCHAR(50),
    Text TEXT,
    Empfaenger_ID INT,
    Termin_ID INT,
    FOREIGN KEY (Empfaenger_ID) REFERENCES Student(ID),
    FOREIGN KEY (Termin_ID) REFERENCES Termin(ID)
);

CREATE TABLE Verwalter (
    ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Email VARCHAR(100),
    Passwort VARCHAR(20)
);