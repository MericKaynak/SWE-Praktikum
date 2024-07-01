import axios from 'axios';

export const fetchAllAppointments = async () => {
  try {
    const response = await axios.get('http://localhost:8080/terminplan/fetchAllLp');
    return response.data;
  } catch (error) {
    console.error('Failed to fetch appointments', error);
    throw error;
  }
};
