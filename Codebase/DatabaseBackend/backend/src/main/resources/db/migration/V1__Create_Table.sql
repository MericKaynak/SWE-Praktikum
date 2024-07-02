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

CREATE TABLE Vertretung (
    Vertretung_ID INT,
    Datum DATE,
    Lehrperson VARCHAR(100),
    FOREIGN KEY (Vertretung_ID) REFERENCES Lehrveranstaltung(ID)
);

CREATE TABLE Lehrplantermin (
    ID INT,
    Lehrveranstaltung_ID INT,
    Datum DATE,
    FOREIGN KEY (Lehrveranstaltung_ID) REFERENCES Lehrveranstaltung(ID),
);

CREATE TABLE Semesterstart (
    ID INT,
    Startdatum DATE
)

CREATE OR REPLACE FUNCTION add_student_to_courses() RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO Besuchen (Student_ID, Lehrveranstaltung_ID)
    SELECT NEW.ID, LV.ID
    FROM Lehrveranstaltung LV
    WHERE LV.Fachbereich = (SELECT Studiengang FROM Student WHERE ID = NEW.ID);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_student_insert
AFTER INSERT ON Student
FOR EACH ROW
EXECUTE FUNCTION add_student_to_courses();
