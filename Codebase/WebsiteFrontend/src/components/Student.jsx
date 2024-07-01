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
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
  Button,
  AppBar,
  Toolbar,
  Typography,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import LoginModal from "./LoginModal.jsx";

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
    weekStart.setDate(currentMonday.getDate() + i * 7);
    weeks.push(weekStart.toISOString().split("T")[0]);
  }

  return weeks;
};

const Student = () => {
  const [appointments, setAppointments] = useState([]);
  const [currentDate, setCurrentDate] = useState(
    getMonday(new Date()).toISOString().split("T")[0]
  );
  const [addedAppointment, setAddedAppointment] = useState({});
  const [appointmentChanges, setAppointmentChanges] = useState({});
  const [editingAppointment, setEditingAppointment] = useState(undefined);
  const [weeks, setWeeks] = useState(generateWeeks());
  const [showLoginModal, setShowLoginModal] = useState(false);
  const [loginError, setLoginError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      setShowLoginModal(true);
    }
  }, []);

  const handleLoginClose = () => {
    setShowLoginModal(false);
  };

  const handleLoginOpen = () => {
    setShowLoginModal(true);
  };

  const sendLogin = async (email, password) => {
    if (!email.endsWith("@stud.hn.de")) {
      setLoginError("Email must end with @stud.hn.de");
      return;
    }
    console.log("Email ist ok", email);
    try {
      const response = await axios.post(
        "http://localhost:8080/terminplan/login",
        { email, password }
      );
      localStorage.setItem("token", response.data.token);
      localStorage.setItem("loginTimestamp", new Date().getTime());
      setShowLoginModal(false);
    } catch (error) {
      console.error("Login failed", error);
    }
  };

  const commitChanges = ({ added, changed, deleted }) => {
    setAppointments((prevAppointments) => {
      let data = prevAppointments;
      if (added) {
        const startingAddedId =
          data.length > 0 ? data[data.length - 1].id + 1 : 0;
        data = [...data, { id: startingAddedId, ...added }];
      }
      if (changed) {
        data = data.map((appointment) =>
          changed[appointment.id]
            ? { ...appointment, ...changed[appointment.id] }
            : appointment
        );
      }
      if (deleted !== undefined) {
        data = data.filter((appointment) => appointment.id !== deleted);
      }
      return data;
    });
  };

  const handleWeekChange = (event) => {
    setCurrentDate(event.target.value);
  };

  return (
    <div style={{ height: "100vh", display: "flex", flexDirection: "column" }}>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" style={{ flexGrow: 1 }}>
            Student Scheduler
          </Typography>
          <Button color="inherit" onClick={() => navigate("/home")}>
            Home
          </Button>
          <Button color="inherit" onClick={handleLoginOpen}>
            Login
          </Button>
        </Toolbar>
      </AppBar>
      <LoginModal
        open={showLoginModal}
        onLogin={sendLogin}
        onClose={handleLoginClose}
        loginError={loginError}
      />
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
          </Grid>
          <DxScheduler data={appointments} height="calc(100vh - 112px)">
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
            <WeekView startDayHour={8} endDayHour={20} />
            <AllDayPanel />
            <EditRecurrenceMenu />
            <ConfirmationDialog />
            <Appointments />
            <AppointmentTooltip />
            <DxAppointmentForm />
          </DxScheduler>
        </Paper>
      </div>
    </div>
  );
};

export default Student;
