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
        `http://localhost:8080/terminplan/fetch/${userId}`
    );

    const data = response.data
    console.log(data)
    const appointments = data.map(item => ({
      id: item.id,
      title: item.titel,
      zeitraumStart: new Date(item.termin.zeitraumStart),
      zeitraumEnd: new Date(item.termin.zeitraumEnd),
      location: item.raum.bezeichnung,
      wochentag: item.termin.wochentag
    }));

    console.log(appointments); // Gibt die Termine im korrekten Format aus
    return appointments; // Gibt das Array mit Terminen zurück
  } catch (error) {
    console.error("Error fetching appointments:", error);
    throw error;
  }
};
