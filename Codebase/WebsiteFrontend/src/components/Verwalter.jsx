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
  Button,
  AppBar,
  Toolbar,
  Typography,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import LoginModal from "./LoginModal.jsx";
import {
  getMonday,
  generateWeeks,
  repeatWeekly,
} from "./AppointmentsFuncs.jsx";

const Verwalter = () => {
  const [appointments, setAppointments] = useState([]);
  const [currentDate, setCurrentDate] = useState(
    getMonday(new Date()).toISOString().split("T")[0]
  );
  const [addedAppointment, setAddedAppointment] = useState({});
  const [appointmentChanges, setAppointmentChanges] = useState({});
  const [editingAppointment, setEditingAppointment] = useState(undefined);
  const [weeks, setWeeks] = useState(generateWeeks());
  const [selectedUser, setSelectedUser] = useState("");
  const [professors, setProfessors] = useState([]);
  const [loadingProfessors, setLoadingProfessors] = useState(true);
  const [showLoginModal, setShowLoginModal] = useState(false);
  const navigate = useNavigate();

  const createSchedule = async () => {
    try {
      const currentDate = new Date();
      const startDatum = new Date();
      const endDatum = new Date();
      console.log("Plan wurde erstellt");
      startDatum.setDate(currentDate.getDate() - 12 * 7); // 12 weeks ago
      endDatum.setDate(currentDate.getDate() + 12 * 7); // 12 weeks ahead

      const formattedStartDatum = startDatum.toISOString().split("T")[0];
      const formattedEndDatum = endDatum.toISOString().split("T")[0];

      const data = {
        startDatum: formattedStartDatum,
        endDatum: formattedEndDatum,
      };

      const response = await axios.post(
        "http://localhost:8080/terminplan/create",
        data
      );
      console.log("Schedule created successfully", response);
    } catch (error) {
      console.error("Error creating schedule", error);
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      setShowLoginModal(true);
    }
  }, []);

  useEffect(() => {
    const fetchProfessors = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/terminplan/fetchAllLp"
        );
        setProfessors(response.data);
        setLoadingProfessors(false);
      } catch (error) {
        console.error("Error fetching professors:", error);
        setLoadingProfessors(false);
      }
    };

    fetchProfessors();
  }, []);

  const handleLoginClose = () => setShowLoginModal(false);

  const handleLoginOpen = () => setShowLoginModal(true);

  const sendLogin = async (email, password) => {
    if (!email.endsWith("@hs-niederrhein.de")) {
      console.error("Email must end with @hs-niederrhein.de");
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/terminplan/login",
        { email, password }
      );

      // Überprüfung des HTTP-Statuscodes der Antwort
      if (response.status === 200) {
        localStorage.setItem("token", response.data.token);
        localStorage.setItem("loginTimestamp", new Date().getTime());
        setShowLoginModal(false);
        fetchAppointments();
      } else {
        // Behandlung des Falles, dass der Server nicht mit 200 OK antwortet
        setLoginError("Login failed: Invalid credentials or other error");
      }
    } catch (error) {
      console.error("Login failed", error);
      setLoginError("Login failed: Server error or network issue");
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

  const handleUserChange = (event) => {
    setSelectedUser(event.target.value);
    // Fetch appointments based on selected user
    fetchAppointments(event.target.value);
  };

  const fetchAppointments = async (userId) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/terminplan/fetch/${userId}`
      );
      const data = response.data;
      console.log(data);

      // Transform the data into the format expected by the Scheduler
      const appointments = data.properties?.map((lecture) => ({
        id: lecture.id,
        title: lecture.titel,
        startDate: new Date(`2024-07-01T${lecture.termin.zeitraumStart}`), // Replace `2023-01-02` with the appropriate start date
        endDate: new Date(`2024-07-01T${lecture.termin.zeitraumEnd}`), // Replace `2023-01-02` with the appropriate end date
        location: lecture.raum.bezeichnung,
        professorId: lecture.lehrperson.id,
        professorName: lecture.lehrperson.name,
      }));

      setAppointments(appointments);
    } catch (error) {
      console.error("Error fetching appointments:", error);
    }
  };

  return (
    <div style={{ height: "100vh", display: "flex", flexDirection: "column" }}>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" style={{ flexGrow: 1 }}>
            Verwalter Scheduler
          </Typography>
          <Button color="inherit" onClick={createSchedule}>
            Erstelle Plan
          </Button>
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
        onClose={handleLoginClose}
        onLogin={sendLogin}
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
            <Grid item xs={8}>
              <FormControl fullWidth>
                <InputLabel id="user-select-label">Lehrperson</InputLabel>
                <Select
                  labelId="user-select-label"
                  value={selectedUser}
                  onChange={handleUserChange}
                  disabled={loadingProfessors}
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
            <EditingState
              onCommitChanges={commitChanges}
              addedAppointment={addedAppointment}
              onAddedAppointmentChange={setAddedAppointment}
              appointmentChanges={appointmentChanges}
              onAppointmentChangesChange={setAppointmentChanges}
              editingAppointment={editingAppointment}
              onEditingAppointmentChange={setEditingAppointment}
            />
            <WeekView startDayHour={8} endDayHour={20} firstDayOfWeek={1} />
            <AllDayPanel />
            <EditRecurrenceMenu />
            <ConfirmationDialog />
            <Appointments />
            <AppointmentTooltip showOpenButton showDeleteButton />
            <DxAppointmentForm />
          </DxScheduler>
        </Paper>
      </div>
    </div>
  );
};

export default Verwalter;
