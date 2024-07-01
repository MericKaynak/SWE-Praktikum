import React from 'react';
import './Home.css';

function Home() {
  return (
    <div className="home-container">
        <div className="home-content">
            <h2>Willkommen!</h2>
            <button className="home-button"><a href="/student">Studenten Pläne</a></button>
            <button className="home-button"><a href="/lehrpersonen">Lehrpersonen Pläne</a></button>
            <button className="home-button"><a href="/verwalter">Verwaltung</a></button>
        </div>
    </div>
  );
}

export default Home;
