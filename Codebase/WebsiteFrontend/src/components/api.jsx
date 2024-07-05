import axios from "axios";
import {json} from "react-router-dom";

export const fetchProfessors = async () => {
  try {
    const response = await axios.get(
        "http://localhost:8080/terminplan/fetchAllLp"
    );
    return response.data;
  } catch (error) {
    console.error("Failed to fetch professors", error);
    throw error;
  }
};

export const fetchAppointments = async (userId) => {
  try {
    let response = await axios.get(
        `http://localhost:8080/terminplan/fetch/${userId}`
    );
    const data = response.data

    const appointments = data.map(item => ({
      Id: item.id,
      Title: item.titel,
      ZeitraumStart: item.termin.zeitraumStart,
      ZeitraumEnd: item.termin.zeitraumEnd,
      Location: item.raum.bezeichnung,
      Wochentag: item.termin.wochentag,
      ProfessorId: item.lehrperson.id,
      ProfessorName:item.lehrperson.name,
    }));
    return appointments; // Gibt das Array mit Terminen zurück
  } catch (error) {
    console.error("Error fetching appointments:", error);
    throw error;
  }
};

export const createSchedule = async () => {
  try {
    const currentDate = new Date();
    const startDatum = new Date();
    const endDatum = new Date();
    console.log("Plan wurde erstellt");
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