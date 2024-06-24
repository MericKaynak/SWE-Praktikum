-- Tabelle: Student
DROP TABLE Student;
DROP TABLE Lehrperson;
DROP TABLE Raum;
DROP TABLE Terminplan;
DROP TABLE Lehrveranstaltung;
DROP TABLE Benachrichtigung;
DROP TABLE Besuchen;

CREATE TABLE Student (
    ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Email VARCHAR(100),
    Studiengang VARCHAR(100)
);

-- Tabelle: Lehrperson
CREATE TABLE Lehrperson (
    ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Email VARCHAR(100),
    Rolle VARCHAR(50),
    Verfügbarkeit VARCHAR(100)
);

-- Tabelle: Raum
CREATE TABLE Raum (
    ID INT PRIMARY KEY,
    Bezeichnung VARCHAR(100),
    Kapazität INT,
    Standort VARCHAR(100)
);

-- Tabelle: Terminplan
CREATE TABLE Terminplan (
    ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Zeitraum_Start DATE,
    Zeitraum_End DATE,
    Raum_ID INT,
    FOREIGN KEY (Raum_ID) REFERENCES Raum(ID)
);

-- Tabelle: Lehrveranstaltung
CREATE TABLE Lehrveranstaltung (
    ID INT PRIMARY KEY,
    Titel VARCHAR(100),
    Fachbereich VARCHAR(100),
    Dauer INT,
    Raum_ID INT,
    Terminplan_ID INT,
    Betreuende_Person_ID INT,
    FOREIGN KEY (Raum_ID) REFERENCES Raum(ID),
    FOREIGN KEY (Terminplan_ID) REFERENCES Terminplan(ID),
    FOREIGN KEY (Betreuende_Person_ID) REFERENCES Lehrperson(ID)
);

-- Tabelle: Benachrichtigung
CREATE TABLE Benachrichtigung (
    ID INT PRIMARY KEY,
    Nachrichtentyp VARCHAR(50),
    Text TEXT,
    Empfänger_ID INT,
    Terminplan_ID INT,
    FOREIGN KEY (Empfänger_ID) REFERENCES Student(ID),
    FOREIGN KEY (Terminplan_ID) REFERENCES Terminplan(ID)
);

-- Beziehungstabelle für Studenten und Lehrveranstaltungen
CREATE TABLE Besuchen (
    Student_ID INT,
    Lehrveranstaltung_ID INT,
    PRIMARY KEY (Student_ID, Lehrveranstaltung_ID),
    FOREIGN KEY (Student_ID) REFERENCES Student(ID),
    FOREIGN KEY (Lehrveranstaltung_ID) REFERENCES Lehrveranstaltung(ID)
);