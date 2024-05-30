# Anforderung
## Funktionale Anforderung
##### Priorität Keywords:
- **MUSS** - Muss Anforderung: Notwendige Anforderungen die erfüllt werden müssen. 
- **SOLL** - Soll Anforderung: Anforderungen bezüglich der Mindesterfüllung.
- **KANN** - Kann Anforderung: Wunschanforderungen, die unter Umständen ausgelassen werden können.

| ID | Anforderung | Beschreibung | Prio. |
| -- | ----------- | ------------ | ----- |
| FA_1.0 |**Terminplanung**| Die Software soll in der Lage sein, einen Einsatzplan für die Mitarbeiter zu erstellen, der zeigt, welche Lehrveranstaltungen sie betreuen sollen. | MUSS |
| FA_1.1 | **Änderungsmanagement** | Die Software muss Änderungen im Plan berücksichtigen können, z.B. durch Krankmeldungen, Fortbildungen oder Tagungen der Mitarbeiter. | MUSS |
| FA_1.2 | **Benachrichtigungen**| Mitarbeiter und Studierende sollen über Änderungen im Plan informiert werden. | SOLL |
| FA_1.3 | **Benutzerzugriff**| Mitarbeiter und Studierende sollen auf ihre individuellen Pläne zugreifen können. | SOLL |
| FA_1.4 | **Ausfall & Vetretung**| Bei Ausfall eines Lehrbeauftragten soll ein anderer eine Benachrichtung erhalten um diese veranstaltung zu vertreten. | SOLL |
| FA_1.5 | **Sonderveranstaltungen**| Die Software soll Sonderveranstaltungen, wie z.B. Kurse für Firmen oder Schülergruppen, verwalten können. | KANN  |
| FA_1.6 | **Automatische Aktualisierung**| Die Software soll sich automatisch aktualisieren, wenn neue Informationen oder Änderungen vorliegen. | SOLL  |
| FA_1.7 | **KI-Unterstützung**| Mögliche Integration einer KI, um Sprachnachrichten automatisch zu transkribieren und in Textform an die Sekretärin weiterzuleiten. | SOLL |


## Nicht-funktionale Anforderungen:

| ID | Anforderung | Beschreibung |
| ----------------------- | --- | ------------- |
|NFA_1.0| **Benutzerfreundlichkeit**|Die Software soll eine intuitive Benutzeroberfläche haben.|
|NFA_2.0| **Zuverlässigkeit**|Die Software muss zuverlässig arbeiten und darf keine Ausfälle verursachen.|
|NFA_3.0| **Performance**|Die Software muss schnell reagieren und Pläne zügig aktualisieren können.|
|NFA_4.0| **Skalierbarkeit**|Die Software soll auch bei weiterem Wachstum der Hochschule problemlos funktionieren.|
|NFA_5.0| **Datensicherheit**|Die Software muss sicherstellen, dass alle Daten geschützt sind und nur autorisierte Benutzer Zugriff haben.|
