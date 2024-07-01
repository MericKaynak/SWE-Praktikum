import React, { useState, useEffect } from "react";
import Paper from "@mui/material/Paper";
import {
  ViewState,
  EditingState,
  IntegratedEditing,
} from "@devexpress/dx-react-scheduler";
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
  Button,
  AppBar,
  Toolbar,
  Typography,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import LoginModal from "./LoginModal.jsx";
import { getMonday, generateWeeks, repeatWeekly } from "./AppointmentsFuncs.jsx";

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
    try {
      const response = await axios.post(
        "http://localhost:8080/terminplan/login",
        { email, password }
      );
      localStorage.setItem("token", response.data.token);
      localStorage.setItem("loginTimestamp", new Date().getTime());
      setShowLoginModal(false);
      fetchAppointments();
    } catch (error) {
      console.error("Login failed", error);
    }
  };

  const fetchAppointments = async () => {
    try {
      const response = await axios.get("http://localhost:8080/terminplan/appointments");
      setAppointments(response.data);
    } catch (error) {
      console.error("Error fetching appointments:", error);
    }
  };

  const handleWeekChange = (event) => {
    setCurrentDate(event.target.value);
  };

  const commitChanges = ({ added, changed, deleted }) => {
    // Handle CRUD operations on appointments
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
            <IntegratedEditing />
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
