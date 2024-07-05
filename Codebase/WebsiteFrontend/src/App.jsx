import React, { useEffect } from "react";
import { useNavigate, Route, Routes } from "react-router-dom";
import Scheduler from "./components/Student.jsx";
import Verwalter from "./components/Verwalter.jsx";
import Home from "./components/Home.jsx";
import Student from "./components/Student.jsx";
import Lehrpersonen from "./components/Lerpersonen.jsx";
import AddRemoveLehrperson from "./components/AddRemoveLehrperson.jsx";
function App() {
  const navigate = useNavigate();

  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route path="/student" element={<Student />} />
        <Route path="/verwalter" element={<Verwalter />} />
        <Route path="/lehrpersonen" element={<Lehrpersonen />} />
        <Route path="/addRemoveLehrperson" element={<AddRemoveLehrperson />} />
      </Routes>
    </div>
  );
}

export default App;
