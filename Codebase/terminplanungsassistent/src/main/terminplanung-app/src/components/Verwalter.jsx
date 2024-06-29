import React, { useState, useEffect } from 'react';
import Paper from '@mui/material/Paper';
import { ViewState, EditingState } from '@devexpress/dx-react-scheduler';
import {
  Scheduler as DxScheduler,
  Appointments,
  AppointmentForm as DxAppointmentForm,
  AppointmentTooltip,
  WeekView,
  EditRecurrenceMenu,
  AllDayPanel,
  ConfirmationDialog,
} from '@devexpress/dx-react-scheduler-material-ui';
import { MenuItem, Select, FormControl, InputLabel, Grid } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const getMonday = (date) => {
  date = new Date(date);
  const day = date.getDay(),
        diff = date.getDate() - day + (day === 0 ? -6 : 1);
  return new Date(date.setDate(diff));
};

const generateWeeks = () => {
  const weeks = [];
  const today = new Date();
  const currentMonday = getMonday(today);

  for (let i = -12; i <= 12; i++) {
    const weekStart = new Date(currentMonday);
    weekStart.setDate(currentMonday.getDate() + (i * 7));
    weeks.push(weekStart.toISOString().split('T')[0]);
  }

  return weeks;
};

const appointmentData = [
  {
    id: 1,
    title: "Kiffologie",
    startDate: new Date("2024-06-24T08:00:00"),
    endDate: new Date("2024-06-24T10:00:00"),
    location: "F303, Krefeld",
    professorId: 69,
    professorName: "Jaman"
  },
  {
    id: 2,
    title: "ET2",
    startDate: new Date("2024-06-24T10:00:00"),
    endDate: new Date("2024-06-24T12:00:00"),
    location: "F303, Krefeld",
    professorId: 69,
    professorName: "Jaman"
  }
];

const professors = [
  { id: 69, name: 'Jaman' },
  { id: 2, name: 'Professor B' },
  { id: 3, name: 'Professor C' },
  // Add more professors as needed
];

const Verwalter = () => {
  const [appointments, setAppointments] = useState([]);
  const [currentDate, setCurrentDate] = useState(getMonday(new Date()).toISOString().split('T')[0]);
  const [addedAppointment, setAddedAppointment] = useState({});
  const [appointmentChanges, setAppointmentChanges] = useState({});
  const [editingAppointment, setEditingAppointment] = useState(undefined);
  const [weeks, setWeeks] = useState(generateWeeks);
  const [selectedUser, setSelectedUser] = useState('');
  const [userType, setUserType] = useState('Professor'); // Set initial userType to 'Professor'
  const navigate = useNavigate();

  const commitChanges = ({ added, changed, deleted }) => {
    setAppointments((prevAppointments) => {
      let data = prevAppointments;
      if (added) {
        const startingAddedId = data.length > 0 ? data[data.length - 1].id + 1 : 0;
        data = [...data, { id: startingAddedId, ...added }];
      }
      if (changed) {
        data = data.map(appointment => (
          changed[appointment.id] ? { ...appointment, ...changed[appointment.id] } : appointment));
      }
      if (deleted !== undefined) {
        data = data.filter(appointment => appointment.id !== deleted);
      }
      return data;
    });
  };

  const handleWeekChange = (event) => {
    setCurrentDate(event.target.value);
  };

  const handleUserChange = (event) => {
    setSelectedUser(event.target.value);
    // Filter appointments based on selected user
    const filteredAppointments = appointmentData.filter(app => app.professorId === event.target.value);
    setAppointments(filteredAppointments);
  };

  // Remove handleUserTypeChange function since userType should always be 'Professor'

  return (
    <Paper>
      <Grid container spacing={2} alignItems="center">
        <Grid item xs={4}>
          <FormControl fullWidth>
            <InputLabel id="week-select-label">Woche</InputLabel>
            <Select
              labelId="week-select-label"
              value={currentDate}
              onChange={handleWeekChange}
            >
              {weeks.map((week) => (
                <MenuItem key={week} value={week}>
                  {week}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={8}>
          <FormControl fullWidth>
            <InputLabel id="user-select-label">Lehrperson</InputLabel>
            <Select
              labelId="user-select-label"
              value={selectedUser}
              onChange={handleUserChange}
            >
              {professors.map((professor) => (
                <MenuItem key={professor.id} value={professor.id}>
                  {professor.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
      </Grid>
      <DxScheduler
        data={appointments}
        height={800}
      >
        <ViewState
          currentDate={currentDate}
          onCurrentDateChange={setCurrentDate}
        />
        <EditingState
          onCommitChanges={commitChanges}
          addedAppointment={addedAppointment}
          onAddedAppointmentChange={setAddedAppointment}
          appointmentChanges={appointmentChanges}
          onAppointmentChangesChange={setAppointmentChanges}
          editingAppointment={editingAppointment}
          onEditingAppointmentChange={setEditingAppointment}
        />
        <WeekView
          startDayHour={8}
          endDayHour={20}
        />
        <AllDayPanel />
        <EditRecurrenceMenu />
        <ConfirmationDialog />
        <Appointments />
        <AppointmentTooltip
          showOpenButton
          showDeleteButton
        />
        <DxAppointmentForm />
      </DxScheduler>
    </Paper>
  );
};

export default Verwalter;
