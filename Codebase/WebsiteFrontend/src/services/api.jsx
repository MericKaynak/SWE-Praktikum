import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
  headers: {
    Authorization: `Bearer ${localStorage.getItem('token')}`
  }
});

export const login = (email, password) =>
  api.post('http://localhost:8080/terminplan/login', { email, password });

export const getAppointments = () =>
  api.get('http://localhost:8080/terminplan/fetch');

export const createAppointment = (appointment) =>
  api.post('/appointments', appointment);


