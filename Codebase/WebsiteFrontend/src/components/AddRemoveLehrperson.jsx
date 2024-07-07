import React, { useState, useEffect } from 'react';
import { FormControl, InputLabel, Select, MenuItem, Grid, TextField, Button, Paper, Typography, AppBar, Toolbar, Snackbar } from '@mui/material';
import axios from "axios";
import { useNavigate } from "react-router-dom";
import LoginModal from './LoginModal.jsx';
import { fetchProfessors as fetchProfessorsApi } from './api.jsx';
import loginData from './loginData.json'; // Import der JSON-Datei
import { createSchedule as createScheduleApi } from "./api.jsx";

const AddRemoveProfessors = () => {
  const navigate = useNavigate();
  const [actionType, setActionType] = useState('');
  const [selectedProfessor, setSelectedProfessor] = useState('');
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    rolle: '',
    wochenarbeitsstunden: '',
    startDate: '',
    endDate: ''
  });
  const [professors, setProfessors] = useState([]);
  const [showLoginModal, setShowLoginModal] = useState(true);
  const [selectedUser, setSelectedUser] = useState("");
  const [creatingSchedule, setCreatingSchedule] = useState(false);
  const [scheduleCreated, setScheduleCreated] = useState(false);

  useEffect(() => {
    const fetchProfessors = async () => {
      try {
        const data = await fetchProfessorsApi();
        setProfessors(data);
      } catch (error) {
        console.error("Error fetching professors:", error);
      }
    };
    fetchProfessors();
  }, []);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      setShowLoginModal(true);
    }
  }, []);

  const handleLoginClose = () => console.log();

  const handleLoginOpen = () => setShowLoginModal(true);

  const sendLogin = async (email, password) => {
    if (!email.endsWith('@hs-niederrhein.de')) {
      console.error('Email must end with @hs-niederrhein.de');
      return;
    }
    try {
      const user = loginData.users.find(user => user.email === email && user.password === password);
      if (user) {
        localStorage.setItem('token', 'fake-jwt-token');
        localStorage.setItem('loginTimestamp', new Date().getTime());
        setShowLoginModal(false);
        console.log('Login successful');
      } else {
        console.error('Login failed: Incorrect email or password');
      }
    } catch (error) {
      console.error("Login failed", error);
      setLoginError("Login failed: Server error or network issue");
    }
  };

  const handleProfessorChange = (event) => {
    setSelectedUser(event.target.value);
  };

  const handleActionChange = (event) => {
    setActionType(event.target.value);
  };

  const handleFormChange = (event) => {
    const { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleFormSubmit = async (event) => {
    event.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/professors/add', formData);
      setFormData({
        name: '',
        email: '',
        rolle: '',
        wochenarbeitsstunden: '',
        startDate: '',
        endDate: ''
      });
      console.log('Successfully added professor');
    } catch (error) {
      console.error('Error adding professor:', error);
    }
  };

  const handleActionSubmit = async () => {
    if (actionType === 'remove' && selectedUser !== '') {
      try {
        await axios.post(`http://localhost:8080/terminplan/delete/${selectedUser}`);
        console.log(`Removed Professor: ${selectedUser}`);
        setSelectedUser('');
        setActionType('');
      } catch (error) {
        console.error('Error deleting professor:', error);
      }
    } else if (actionType === 'krankmelden' && selectedUser !== '') {
      try {
        const datumList = [formData.startDate, formData.endDate];
        await axios.put(`http://localhost:8080/terminplan/notify/${selectedUser}`, datumList);
        console.log(`Marked Professor ${selectedUser} as sick from ${formData.startDate} to ${formData.endDate}`);
        setSelectedUser('');
        setActionType('');
        setFormData({ ...formData, startDate: '', endDate: '' });
      } catch (error) {
        console.error('Error marking professor as sick:', error);
      }
    }
  };

  const handleCreateSchedule = async () => {
    setCreatingSchedule(true);
    try {
      await createScheduleApi();
      setScheduleCreated(true);
      console.log("Schedule created successfully");
    } catch (error) {
      console.error("Error creating schedule", error);
    } finally {
      setCreatingSchedule(false);
    }
  };

  return (
      <div style={{ height: '100vh', display: 'flex', flexDirection: 'column' }}>
        <AppBar position="static">
          <Toolbar>
            <Typography variant="h6" style={{ flexGrow: 1 }}>
              Professor Management
            </Typography>
            <Button color="inherit" onClick={() => navigate('/home')}>
              Home
            </Button>
            <Button
                color="inherit"
                onClick={handleCreateSchedule}
                disabled={creatingSchedule}
            >
              {creatingSchedule ? "Erstellen..." : "Erstelle Plan"}
            </Button>
          </Toolbar>
        </AppBar>
        <LoginModal open={showLoginModal} onClose={handleLoginClose} onLogin={sendLogin} />
        <div style={{ flexGrow: 1, padding: '16px' }}>
          <Grid container spacing={2} justifyContent="center">
            <Grid item xs={12}>
              <Paper style={{ padding: '16px' }}>
                <FormControl fullWidth>
                  <InputLabel id="action-type-label">Option</InputLabel>
                  <Select
                      labelId="action-type-label"
                      value={actionType}
                      onChange={handleActionChange}
                  >
                    <MenuItem value="">Option Waehlen</MenuItem>
                    <MenuItem value="add">Lehrperson hinzufuegen</MenuItem>
                    <MenuItem value="remove">Lehrperson entfernen</MenuItem>
                    <MenuItem value="krankmelden">Lehrperson Krankmelden</MenuItem>
                  </Select>
                </FormControl>
              </Paper>
            </Grid>
            {(actionType === 'add' || actionType === 'remove' || actionType === 'krankmelden') && (
                <Grid item xs={12}>
                  <Paper style={{ padding: '16px' }}>
                    {actionType === 'add' && (
                        <>
                          <Typography variant="h6">Add Professor</Typography>
                          <form onSubmit={handleFormSubmit}>
                            <Grid container spacing={2}>
                              <Grid item xs={6}>
                                <TextField
                                    fullWidth
                                    name="name"
                                    label="Name"
                                    value={formData.name}
                                    onChange={handleFormChange}
                                />
                              </Grid>
                              <Grid item xs={6}>
                                <TextField
                                    fullWidth
                                    name="email"
                                    label="Email"
                                    value={formData.email}
                                    onChange={handleFormChange}
                                />
                              </Grid>
                              <Grid item xs={6}>
                                <TextField
                                    fullWidth
                                    name="rolle"
                                    label="Rolle"
                                    value={formData.rolle}
                                    onChange={handleFormChange}
                                />
                              </Grid>
                              <Grid item xs={6}>
                                <TextField
                                    fullWidth
                                    name="wochenarbeitsstunden"
                                    label="Wochenarbeitsstunden"
                                    type="number"
                                    value={formData.wochenarbeitsstunden}
                                    onChange={handleFormChange}
                                />
                              </Grid>
                              <Grid item xs={12}>
                                <Button type="submit" variant="contained" color="primary">
                                  Add Professor
                                </Button>
                              </Grid>
                            </Grid>
                          </form>
                        </>
                    )}
                    {actionType === 'remove' && (
                        <>
                          <Typography variant="h6">Remove Professor</Typography>
                          <FormControl fullWidth>
                            <InputLabel id="professor-select-label">Waehle Professor</InputLabel>
                            <Select
                                labelId="professor-select-label"
                                value={selectedUser}
                                onChange={handleProfessorChange}
                            >
                              <MenuItem value="">Waehle Lehrperson</MenuItem>
                              {professors.map((professor) => (
                                  <MenuItem key={professor.id} value={professor.id}>
                                    {professor.name}
                                  </MenuItem>
                              ))}
                            </Select>
                          </FormControl>
                          <Button
                              variant="contained"
                              color="secondary"
                              style={{ marginLeft: '10px' }}
                              disabled={selectedUser === ''}
                              onClick={handleActionSubmit}
                          >
                            Bestaetigen
                          </Button>
                        </>
                    )}
                    {actionType === 'krankmelden' && (
                        <>
                          <Typography variant="h6">Krankmelden</Typography>
                          <FormControl fullWidth>
                            <InputLabel id="professor-select-label">Select Professor</InputLabel>
                            <Select
                                labelId="professor-select-label"
                                value={selectedUser}
                                onChange={handleProfessorChange}
                            >
                              <MenuItem value="">Select Professor</MenuItem>
                              {professors.map((professor) => (
                                  <MenuItem key={professor.id} value={professor.id}>
                                    {professor.name}
                                  </MenuItem>
                              ))}
                            </Select>
                          </FormControl>
                          <Grid container spacing={2} style={{ marginTop: '10px' }}>
                            <Grid item xs={6}>
                              <TextField
                                  fullWidth
                                  name="startDate"
                                  label="VON"
                                  type="date"
                                  InputLabelProps={{ shrink: true }}
                                  value={formData.startDate}
                                  onChange={handleFormChange}
                              />
                            </Grid>
                            <Grid item xs={6}>
                              <TextField
                                  fullWidth
                                  name="endDate"
                                  label="BIS"
                                  type="date"
                                  InputLabelProps={{ shrink: true }}
                                  value={formData.endDate}
                                  onChange={handleFormChange}
                              />
                            </Grid>
                          </Grid>
                          <Button
                              variant="contained"
                              color="primary"
                              style={{ marginTop: '10px' }}
                              disabled={selectedUser === '' || formData.endDate === '' || formData.startDate === ''}
                              onClick={handleActionSubmit}
                          >
                            Bestaetigen
                          </Button>
                        </>
                    )}
                  </Paper>
                </Grid>
            )}
          </Grid>
          <Snackbar
              open={scheduleCreated}
              autoHideDuration={6000}
              onClose={() => setScheduleCreated(false)}
              message="Plan wurde erfolgreich erstellt"
          />
        </div>
      </div>
  );
};

export default AddRemoveProfessors;
