import React from 'react';
import './Home.css';
import axios from 'axios'

function VerwaltungHome() {
  return (
    <div className="home-container">
        <div className="home-content">
            <h2>Willkommen!<br/> Was moechten Sie verwalten?</h2>
            <button className="home-button"><a href="/verwalter">Stundenpläne ändern</a></button>
            <button className="home-button"><a href="/addRemoveLehrperson">Lehrpersonen verwalten</a></button>
        </div>
    </div>
  );
}

export default VerwaltungHome;
