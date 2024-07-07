import axios from "axios";

export const fetchProfessors = async () => {
  try {
    const response = await axios.get(
        "http://localhost:8080/terminplan/fetchAllLp"
    );

    const sortedProfessors = response.data.sort((a, b) => {
      if (a.name < b.name) {
        return -1;
      }
      if (a.name > b.name) {
        return 1;
      }
      return 0;
    });

    return sortedProfessors;
  } catch (error) {
    console.error("Failed to fetch professors", error);
    throw error;
  }
};

export const fetchAppointments = async (userId) => {
  try {
    let response = await axios.get(
        `http://localhost:8080/terminplan/fetchlp/${userId}`
    );
    const data = response.data;
    const appointments = data.map((item) => ({
      Id: item.id,
      Title: item.lehrveranstaltung.titel,
      Datum: item.datum,
      ZeitraumStart: item.lehrveranstaltung.termin.zeitraumStart,
      ZeitraumEnd: item.lehrveranstaltung.termin.zeitraumEnd,
      Wochentag: item.lehrveranstaltung.termin.wochentag,
      Location: item.lehrveranstaltung.raum.bezeichnung + " " + item.lehrveranstaltung.raum.standort,
      OrigProf: item.lehrveranstaltung.lehrperson.name,
      ProfessorId: item.vertretung?.lehrperson?.id ?? item.lehrveranstaltung.lehrperson.id,
      ProfessorName: item.vertretung?.lehrperson?.name ?? item.lehrveranstaltung.lehrperson.name,
    }));
    return appointments; // Returns the array with appointments
  } catch (error) {
    console.error("Error fetching appointments", error);
    throw error;
  }
};

export const fetchStudentAppointments = async (studid) => {
  try {
    const url = `http://localhost:8080/terminplan/fetchstudent/${studid}`;
    const response = await axios.get(url);
    const data = response.data;

    const appointments = data.map((item) => ({
      Id: item.id,
      Title: item.lehrveranstaltung.titel,
      Datum: item.datum,
      ZeitraumStart: item.lehrveranstaltung.termin.zeitraumStart,
      ZeitraumEnd: item.lehrveranstaltung.termin.zeitraumEnd,
      Wochentag: item.lehrveranstaltung.termin.wochentag,
      Location: item.lehrveranstaltung.raum.bezeichnung + " " + item.lehrveranstaltung.raum.standort,
      OrigProf: item.lehrveranstaltung.lehrperson.name,
      ProfessorId: item.vertretung?.lehrperson?.id ?? item.lehrveranstaltung.lehrperson.id,
      ProfessorName: item.vertretung?.lehrperson?.name ?? item.lehrveranstaltung.lehrperson.name,
    }));

    return appointments; // Returns the array with appointments
  } catch (error) {
    console.error("Error fetching appointments", error);
    throw error;
  }
};

export const createSchedule = async () => {
  try {
    const currentDate = new Date();
    const startDatum = new Date();
    const endDatum = new Date();
    startDatum.setDate(currentDate.getDate() - 12 * 7); // 12 weeks ago
    endDatum.setDate(currentDate.getDate() + 12 * 7); // 12 weeks ahead

    const formattedStartDatum = startDatum.toISOString().split("T")[0];
    const formattedEndDatum = endDatum.toISOString().split("T")[0];

    const data = {
      startDatum: formattedStartDatum,
      endDatum: formattedEndDatum,
    };

    const response = await axios.post(
        "http://localhost:8080/terminplan/create",
        data
    );
    console.log("Schedule created successfully", response);
  } catch (error) {
    console.error("Error creating schedule", error);
  }
};
