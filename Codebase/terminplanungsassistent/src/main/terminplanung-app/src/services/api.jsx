import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
  headers: {
    Authorization: `Bearer ${localStorage.getItem('token')}`
  }
});

export const login = (email, password) =>
  api.post('/auth/login', { email, password });

export const getAppointments = () =>
  api.get('/appointments');

export const createAppointment = (appointment) =>
  api.post('/appointments', appointment);
