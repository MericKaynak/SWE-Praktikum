import axios from "axios";
import { repeatWeekly } from "./AppointmentsFuncs.jsx";

export const fetchProfessors = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/terminplan/fetchAllLp"
    );
    return response.data;
  } catch (error) {
    console.error("Failed to fetch appointments", error);
    throw error;
  }
};

export const fetchAppointments = async (userId) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/terminplan/fetch/${userId}`
    );
    const data = response.data;
    const filteredAppointments = repeatWeekly(
      data.filter((app) => app.professorId === userId)
    );
    setAppointments(filteredAppointments);
  } catch (error) {
    console.error("Error fetching appointments:", error);
  }
};
