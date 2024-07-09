import React, { useState, useEffect } from "react";
import Paper from "@mui/material/Paper";
import { ViewState, EditingState } from "@devexpress/dx-react-scheduler";
import {
  Scheduler as DxScheduler,
  Appointments,
  AppointmentForm as DxAppointmentForm,
  AppointmentTooltip,
  WeekView,
  EditRecurrenceMenu,
  AllDayPanel,
  ConfirmationDialog,
} from "@devexpress/dx-react-scheduler-material-ui";
import {
  MenuItem,
  Select,
  FormControl,
  InputLabel,
  Grid,
  AppBar,
  Toolbar,
  Typography,
  Button,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import { getMonday, generateWeeks } from "./AppointmentsFuncs.jsx";
import {
  fetchAppointments as fetchAppointmentsApi,
  fetchProfessors as fetchProfessorsApi,
} from "./api.jsx";

const Lehrpersonen = () => {
  const [appointments, setAppointments] = useState([]);
  const [currentDate, setCurrentDate] = useState(
      getMonday(new Date()).toISOString().split("T")[0]
  );
  const [addedAppointment, setAddedAppointment] = useState({});
  const [appointmentChanges, setAppointmentChanges] = useState({});
  const [editingAppointment, setEditingAppointment] = useState(undefined);
  const [weeks, setWeeks] = useState(generateWeeks());
  const [professors, setProfessors] = useState([]);
  const [selectedUser, setSelectedUser] = useState("");
  const navigate = useNavigate();

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
    const fetchAppointments = async (userId) => {
      try {
        const data = await fetchAppointmentsApi(userId);
        const formattedAppointments = data.map((appointment) => ({
          id: appointment.Id,
          title: appointment.Title,
          startDate: new Date(appointment.Datum + " " + appointment.ZeitraumStart),
          endDate: new Date(appointment.Datum + " " + appointment.ZeitraumEnd),
          location: appointment.Location,
          professorId: appointment.ProfessorId,
          origProf:appointment.OrigProf,
          professorName: appointment.ProfessorName,
        }));
        setAppointments(formattedAppointments);
      } catch (error) {
        console.error("Error fetching appointments:", error);
      }
    };
    if (selectedUser) {
      fetchAppointments(selectedUser);
    }
  }, [selectedUser]);

  const handleWeekChange = (event) => {
    setCurrentDate(event.target.value);
  };

  const handleUserChange = (event) => {
    setSelectedUser(event.target.value);
  };

  const CustomAppointmentTooltipContent = ({ appointmentData, ...restProps }) => {
    return (
        <AppointmentTooltip.Content {...restProps} appointmentData={appointmentData}>
          <div style={{ padding: "8px" }}>
            <div><strong>Titel:</strong> {appointmentData.title}</div>
            <div><strong>Lehrperson:</strong> {appointmentData.origProf}</div>
            <div><strong>Standort:</strong> {appointmentData.location}</div>
            {appointmentData.professorName !== appointmentData.origProf && (
                <div><strong>Vertretung:</strong> {appointmentData.professorName}</div>
            )}
          </div>
        </AppointmentTooltip.Content>
    );
  };

  return (
      <div style={{ height: "100vh", display: "flex", flexDirection: "column" }}>
        <AppBar position="static">
          <Toolbar>
            <Typography variant="h6" style={{ flexGrow: 1 }}>
              Lehrpersonen Scheduler
            </Typography>
            <Button color="inherit" onClick={() => navigate("/home")}>
              Home
            </Button>
          </Toolbar>
        </AppBar>
        <div style={{ flexGrow: 1 }}>
          <Paper style={{ height: "100%" }}>
            <Grid
                container
                spacing={2}
                alignItems="center"
                style={{ padding: "16px" }}
            >
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
            <DxScheduler data={appointments} height="calc(100vh - 112px)">
              <ViewState
                  currentDate={currentDate}
                  onCurrentDateChange={setCurrentDate}
              />
              <EditingState />
              <WeekView
                  startDayHour={8}
                  endDayHour={20}
                  firstDayOfWeek={1}
                  excludedDays={[0, 6]}
              />
              <AllDayPanel />
              <EditRecurrenceMenu />
              <ConfirmationDialog />
              <Appointments />
              <AppointmentTooltip
                  contentComponent={CustomAppointmentTooltipContent}
              />
              <DxAppointmentForm />
            </DxScheduler>
          </Paper>
        </div>
      </div>
  );
};

export default Lehrpersonen;
