import React, { useEffect } from 'react';
import { useNavigate, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import Scheduler from './components/Scheduler';

function App() {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    const loginTimestamp = localStorage.getItem('loginTimestamp');
    const now = new Date().getTime();

    if (token && loginTimestamp && (now - loginTimestamp) < 1800000) { // 1800000 ms = 30 minutes
      navigate('/scheduler');
    } else {
      localStorage.removeItem('token');
      localStorage.removeItem('loginTimestamp');
      navigate('/login');
    }
  }, [navigate]);

  return (
    <div className="App">
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/scheduler" element={<Scheduler />} />
      </Routes>
    </div>
  );
}

export default App;
