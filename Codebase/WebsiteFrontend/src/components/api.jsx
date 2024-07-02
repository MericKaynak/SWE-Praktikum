import axios from "axios";

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
    return response.data;
  } catch (error) {
    console.error("Error fetching appointments", error);
    throw error;
  }
};
