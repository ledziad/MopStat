import React, { useEffect, useState } from "react";
import axios from "axios";

const WeatherWidget = () => {
  const [weather, setWeather] = useState(null);

  useEffect(() => {
      const token = localStorage.getItem("jwt");
    axios.get("http://localhost:8081/api/weather",{
           headers: { Authorization: `Bearer ${token}` }
        })
      .then(res => setWeather(res.data))
      .catch(() => setWeather({ description: "Brak danych", emoji: "❓" }));
  }, []);

  if (!weather) return <div>Ładowanie pogody…</div>;

  return (
    <div style={{ margin: 24, background: "#eafcf5", borderRadius: 16, padding: 20, boxShadow: "0 2px 8px #d8f6e0" }}>
      <h3>Pogoda w Giżycku</h3>
      <div style={{ fontSize: 42 }}>{weather.emoji}</div>
      <div style={{ fontSize: 22 }}>{weather.temperature ?? "--"}°C</div>
      <div>{weather.description}</div>
    </div>
  );
};

export default WeatherWidget;
