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
