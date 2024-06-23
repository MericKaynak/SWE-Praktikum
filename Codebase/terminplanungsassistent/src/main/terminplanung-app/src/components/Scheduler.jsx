import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AppointmentForm from './AppointmentForm';

const Scheduler = () => {
  const [appointments, setAppointments] = useState([]);

  useEffect(() => {
    const fetchAppointments = async () => {
      try {
        // TODO: Call backend API to get appointments
        const response = await axios.get('/api/appointments', {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        });
        setAppointments(response.data);
      } catch (error) {
        console.error('Failed to fetch appointments', error);
      }
    };

    fetchAppointments();
  }, []);

  return (
    <div>
      <h2>Scheduler</h2>
      <AppointmentForm />
      <ul>
        {appointments.map((appointment) => (
          <li key={appointment.id}>
            {appointment.date} - {appointment.time}: {appointment.description}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Scheduler;
