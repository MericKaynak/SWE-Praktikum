CREATE TABLE Student (
    ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Email VARCHAR(100),
    Studiengang VARCHAR(100),
    Passwort VARCHAR(20)
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
    ID INT PRIMARY KEY,
    Student_ID INT,
    Lehrveranstaltung_ID INT,
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

CREATE SEQUENCE vertretung_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE Vertretung (
    ID INT PRIMARY KEY DEFAULT NEXTVAL('vertretung_seq'),
    Lehrveranstaltung_ID INT,
    Datum DATE,
    Lehrperson_ID INT,
    FOREIGN KEY (Lehrveranstaltung_ID) REFERENCES Lehrveranstaltung(ID),
    FOREIGN KEY (Lehrperson_ID) REFERENCES Lehrperson(ID)
);


CREATE TABLE Lehrplantermin (
    ID INT PRIMARY KEY,
    Lehrveranstaltung_ID INT,
    Datum DATE,
    Vertretung_ID INT DEFAULT NULL,
    FOREIGN KEY (Vertretung_ID) REFERENCES Vertretung(ID),
    FOREIGN KEY (Lehrveranstaltung_ID) REFERENCES Lehrveranstaltung(ID)
);



