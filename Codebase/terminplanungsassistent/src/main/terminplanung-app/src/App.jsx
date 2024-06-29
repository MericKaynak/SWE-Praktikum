import React, { useEffect } from 'react';
import { useNavigate, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import Scheduler from './components/Scheduler';
import Verwalter from "./components/Verwalter.jsx";
function App() {
  const navigate = useNavigate();



  return (
    <div className="App">
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Scheduler />} />
          <Route path="/verwalter" element={<Verwalter/>}/>
      </Routes>
    </div>
  );
}

export default App;
