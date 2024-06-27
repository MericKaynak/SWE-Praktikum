DROP TABLE Student, Lehrperson, Raum, Termin, Lehrveranstaltung, Benachrichtigung, Besuchen CASCADE;

CREATE TABLE Student (
    ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Email VARCHAR(100),
    Studiengang VARCHAR(100)
);

CREATE TABLE Lehrperson (
    ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Email VARCHAR(100),
    Rolle VARCHAR(50),
    Verfügbarkeit VARCHAR(100)
);

CREATE TABLE Raum (
    ID INT PRIMARY KEY,
    Bezeichnung VARCHAR(100),
    Kapazität INT,
    Standort VARCHAR(100)
);

CREATE TABLE Termin(
    ID INT PRIMARY KEY,
    Datum DATE,
    Zeitraum_Start TIME,
    Zeitraum_End TIME,
    Raum_ID INT,
    FOREIGN KEY (Raum_ID) REFERENCES Raum(ID)
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
    Empfänger_ID INT,
    Termin_ID INT,
    FOREIGN KEY (Empfänger_ID) REFERENCES Student(ID),
    FOREIGN KEY (Termin_ID) REFERENCES Termin(ID)
);

CREATE OR REPLACE FUNCTION check_raumbelegung()
RETURNS TRIGGER AS $$
BEGIN
    -- Prüfen, ob die Kombination bereits existiert
    IF EXISTS (
        SELECT 1
        FROM Termin
        WHERE Datum = NEW.Datum
          AND Zeitraum_Start = NEW.Zeitraum_Start
          AND Raum_ID = NEW.Raum_ID
    ) THEN
        RAISE EXCEPTION 'Der Raum ist zu diesem Zeitpunkt bereits belegt!!!';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER raumbelegung
BEFORE INSERT ON Termin
FOR EACH ROW
EXECUTE FUNCTION check_raumbelegung();

\COPY student FROM 'datamart/student.csv' WITH (FORMAT csv, HEADER true);
\COPY benachrichtigung FROM 'datamart/benachrichtigung.csv' WITH (FORMAT csv, HEADER true);
\COPY raum FROM 'datamart/raum.csv' WITH (FORMAT csv, HEADER true);
\COPY lehrperson FROM 'datamart/lehrperson.csv' WITH (FORMAT csv, HEADER true);
\COPY termin FROM 'datamart/termin.csv' WITH (FORMAT csv, HEADER true);
\COPY lehrveranstaltung FROM 'datamart/lehrveranstaltung.csv' WITH (FORMAT csv, HEADER true);
\COPY besuchen FROM 'datamart/besuchen.csv' WITH (FORMAT csv, HEADER true);



