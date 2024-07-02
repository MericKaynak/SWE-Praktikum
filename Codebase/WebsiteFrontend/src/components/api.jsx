import axios from "axios";
import { repeatWeekly } from "./AppointmentsFuncs.jsx";

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
    const response = await axios.get(
      `http://localhost:8080/terminplan/fetchLp/${userId}`
    );
    const data = response.data.map((appointment) => ({
      id: appointment.id,
      title: appointment.titel,
      startDate: new Date(
        `${getMonday(new Date()).toISOString().split("T")[0]}T${appointment.termin.zeitraumStart}`
      ),
      endDate: new Date(
        `${getMonday(new Date()).toISOString().split("T")[0]}T${appointment.termin.zeitraumEnd}`
      ),
      location: appointment.raum.bezeichnung,
      professorId: appointment.lehrperson.id,
      professorName: appointment.lehrperson.name,
    }));
    return repeatWeekly(data);
  } catch (error) {
    console.error("Error fetching appointments", error);
    throw error;
  }
};
