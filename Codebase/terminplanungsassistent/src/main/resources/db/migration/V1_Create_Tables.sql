DROP TABLE Student, Lehrperson, Raum, Terminplan, Lehrveranstaltung, Benachrichtigung, Besuchen CASCADE;

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

CREATE TABLE Terminplan (
    ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Zeitraum_Start DATE,
    Zeitraum_End DATE,
    Raum_ID INT,
    FOREIGN KEY (Raum_ID) REFERENCES Raum(ID)
);

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

CREATE TABLE Benachrichtigung (
    ID INT PRIMARY KEY,
    Nachrichtentyp VARCHAR(50),
    Text TEXT,
    Empfänger_ID INT,
    Terminplan_ID INT,
    FOREIGN KEY (Empfänger_ID) REFERENCES Student(ID),
    FOREIGN KEY (Terminplan_ID) REFERENCES Terminplan(ID)
);

CREATE TABLE Besuchen (
    Student_ID INT,
    Lehrveranstaltung_ID INT,
    PRIMARY KEY (Student_ID, Lehrveranstaltung_ID),
    FOREIGN KEY (Student_ID) REFERENCES Student(ID),
    FOREIGN KEY (Lehrveranstaltung_ID) REFERENCES Lehrveranstaltung(ID)
);

\COPY student FROM 'datamart/student.csv' WITH (FORMAT csv, HEADER true);
\COPY benachrichtigung FROM 'datamart/benachrichtigung.csv' WITH (FORMAT csv, HEADER true);
\COPY besuchen FROM 'datamart/besuchen.csv' WITH (FORMAT csv, HEADER true);
\COPY lehrperson FROM 'datamart/lehrperson.csv' WITH (FORMAT csv, HEADER true);
\COPY lehrveranstaltung FROM 'datamart/lehrveranstaltung.csv' WITH (FORMAT csv, HEADER true);
\COPY raum FROM 'datamart/raum.csv' WITH (FORMAT csv, HEADER true);
\COPY terminplan FROM 'datamart/terminplan.csv' WITH (FORMAT csv, HEADER true);

