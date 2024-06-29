-- INSERT INTO benachrichtigung VALUES ();

-- INSERT INTO besuchen (Student_ID, Lehrveranstaltung_ID) 
-- VALUES
--      (4321, 1);

INSERT INTO lehrperson (ID, Name, Email, Rolle, Wochenarbeitsstunden)
VALUES
     (069, 'Jaman', 'jaman@stud.hn.de', 'Lehrer', 0),
     (1, 'Max Mustermann', 'max.mustermann@hs-niederrhein.de', 'Professor', 0),
    (2, 'Erika Musterfrau', 'erika.musterfrau@hs-niederrhein.de', 'Tutor', 0),
    (3, 'Hans Schmidt', 'hans.schmidt@hs-niederrhein.de', 'Dozent', 0),
    (4, 'Anna Berger', 'anna.berger@hs-niederrhein.de', 'Professor', 0),
    (5, 'Thomas Mayer', 'thomas.mayer@hs-niederrhein.de', 'Tutor', 0),
    (6, 'Monika Huber', 'monika.huber@hs-niederrhein.de', 'Dozent', 0),
    (7, 'Matthias Fischer', 'matthias.fischer@hs-niederrhein.de', 'Professor', 0),
    (8, 'Sabine Wolf', 'sabine.wolf@hs-niederrhein.de', 'Tutor', 0),
    (9, 'Jürgen Schulz', 'juergen.schulz@hs-niederrhein.de', 'Dozent', 0),
    (10, 'Petra Wagner', 'petra.wagner@hs-niederrhein.de', 'Professor', 0);

INSERT INTO raum (ID, Bezeichnung, Kapazität, Standort)
VALUES 
     (1, 'F303', 200, 'Krefeld'),
     (2, 'F302', 200, 'Krefeld'),
     (3, 'B101', 200, 'Gladbach'),

INSERT INTO termin (ID, Datum, Zeitraum_Start, Zeitraum_End, Raum_ID)
VALUES
    ;

INSERT INTO lehrveranstaltung (ID, Titel, Fachbereich, Dauer, Raum_ID, Termin_ID, Lehrperson_ID)
VALUES 
    (1, 'Kiffologie', 'FB69', 2, 1, NULL, NULL),
    (2, 'ET2', 'FB03', 2, 1, NULL, NULL),
    (3, 'Mathematik I', 'FB01', 2, 2, NULL, NULL),
    (4, 'Physik II', 'FB02', 2, 2, NULL, NULL),
    (5, 'Informatik Grundlagen', 'FB03', 2, 2, NULL, NULL),
    (6, 'Chemie für Ingenieure', 'FB04', 2, 3, NULL, NULL),
    (7, 'Elektrotechnik Praktikum', 'FB05', 2, 3, NULL, NULL),
    (8, 'Medizinische Biotechnologie', 'FB06', 2, 3, NULL, NULL),
    (9, 'Kunstgeschichte', 'FB07', 2, 1, NULL, NULL),
    (10, 'Wirtschaftsethik', 'FB08', 2, 1, NULL, NULL),
    (11, 'Politikwissenschaften', 'FB09', 2, 1, NULL, NULL),
    (12, 'Psychologie II', 'FB10', 2, 2, NULL, NULL),
    (13, 'Soziologie der Arbeit', 'FB11', 2, 2, NULL, NULL),
    (14, 'Internationale Beziehungen', 'FB12', 2, 2, NULL, NULL),
    (15, 'Literaturwissenschaften', 'FB13', 2, 3, NULL, NULL),
    (16, 'Geschichte der Architektur', 'FB14', 2, 3, NULL, NULL),
    (17, 'Philosophie der Technik', 'FB15', 2, 3, NULL, NULL),
    (18, 'Sportwissenschaften', 'FB16', 2, 1, NULL, NULL),
    (19, 'Medieninformatik', 'FB17', 2, 1, NULL, NULL),
    (20, 'Maschinenbau', 'FB18', 2, 1, NULL, NULL),
    (21, 'Bauingenieurwesen', 'FB19', 2, 2, NULL, NULL),
    (22, 'Umwelttechnik', 'FB20', 2, 2, NULL, NULL),
    (23, 'Psychologie I', 'FB10', 2, 3, NULL, NULL),
    (24, 'Medienwissenschaften', 'FB13', 2, 3, NULL, NULL),
    (25, 'Designtheorie', 'FB14', 2, 3, NULL, NULL),
    (26, 'Robotik und Automatisierung', 'FB18', 2, 1, NULL, NULL),
    (27, 'Digitale Signalverarbeitung', 'FB19', 2, 1, NULL, NULL),
    (28, 'Nachhaltige Energie', 'FB20', 2, 1, NULL, NULL),
    (29, 'Biochemie', 'FB04', 2, 2, NULL, NULL),
    (30, 'Grundlagen der Betriebswirtschaftslehre', 'FB08', 2, 2, NULL, NULL),
    (31, 'Medienethik', 'FB13', 2, 2, NULL, NULL),
    (32, 'Archäologie der Antike', 'FB14', 2, 3, NULL, NULL),
    (33, 'Politische Ökonomie', 'FB09', 2, 3, NULL, NULL),
    (34, 'Kunstgeschichte der Renaissance', 'FB07', 2, 1, NULL, NULL),
    (35, 'Psychologie III', 'FB10', 2, 1, NULL, NULL),
    (36, 'Medieninformatik II', 'FB17', 2, 1, NULL, NULL),
    (37, 'Maschinendynamik', 'FB18', 2, 2, NULL, NULL),
    (38, 'Baurecht', 'FB19', 2, 2, NULL, NULL),
    (39, 'Umwelttechnik II', 'FB20', 2, 2, NULL, NULL),
    (40, 'Mikrobiologie', 'FB04', 2, 3, NULL, NULL),
    (41, 'Marketing Management', 'FB08', 2, 3, NULL, NULL),
    (42, 'Filmgeschichte', 'FB13', 2, 3, NULL, NULL),
    (43, 'Architekturtheorie', 'FB14', 2, 1, NULL, NULL),
    (44, 'Internationale Politik', 'FB09', 2, 1, NULL, NULL),
    (45, 'Moderne Kunst', 'FB07', 2, 2, NULL, NULL),
    (46, 'Psychologie IV', 'FB10', 2, 2, NULL, NULL),
    (47, 'Angewandte Kryptographie', 'FB17', 2, 2, NULL, NULL),
    (48, 'Thermodynamik', 'FB18', 2, 3, NULL, NULL),
    (49, 'Baukonstruktion', 'FB19', 2, 3, NULL, NULL),
    (50, 'Abfallwirtschaft', 'FB20', 2, 1, NULL, NULL),
    (51, 'Genetik', 'FB04', 2, 1, NULL, NULL),
    (52, 'Internationale Finanzmärkte', 'FB08', 2, 2, NULL, NULL);
