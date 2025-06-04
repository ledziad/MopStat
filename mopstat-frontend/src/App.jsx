import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import DogsPage from "./pages/DogsPage";
import AddDogPage from "./pages/AddDogPage";
import DogDetailsPage from "./pages/DogDetailsPage";
import AddRecordPage from "./pages/AddRecordPage";
import EditDogPage from "./pages/EditDogPage";
import EditDailyRecordPage from "./pages/EditDailyRecordPage";
import pugIcon from "./assets/pug.png";

// Dodaj cytaty do losowania
const quotes = [
  "Mops to nie pies, to styl życia! 😎",
  "Nigdy nie jesteś sam, gdy masz mopsa 🐾",
  "Każdy dzień z mopsem = dzień wygrany! 🏆",
  "Kupa szczęścia zaczyna się od mopsa 💩💛",
  "Mops nie pyta, mops rozumie 🐶💬"
];
const randomQuote = quotes[Math.floor(Math.random() * quotes.length)];

function App() {
  return (
    <BrowserRouter>
      {/* --- Mopsowy nagłówek --- */}
      <div style={{ textAlign: "center", margin: "32px 0" }}>
          <h1 style={{ fontSize: "2.3rem" }}>
             <img
               src={pugIcon}
               alt="mops"
               style={{
                 height: "1.5em",
                 verticalAlign: "middle",
                 marginRight: "0.2em",
                 marginBottom: "0.13em"
               }}
             />
             <span style={{ color: "#876445" }}>MopStat</span> — Twój dziennik opieki nad mopsami 🐾
           </h1>
        <p style={{ fontStyle: "italic", color: "#808080", marginTop: 12 }}>
          „Najlepszy dzień zaczyna się od spaceru z mopsem!” 🚶‍♂️🐾
        </p>
        <p style={{ color: "#6c757d", marginTop: 24 }}>{randomQuote}</p>
      </div>
      {/* --- Koniec nagłówka --- */}

      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/dogs" element={<DogsPage />} />
        <Route path="/add-dog" element={<AddDogPage />} />
        <Route path="/dogs/:id" element={<DogDetailsPage />} />
        <Route path="/add-record/:dogId" element={<AddRecordPage />} />
        {/* Przekierowanie, gdy ścieżka nieznana */}
        <Route path="*" element={<Navigate to="/login" />} />
        <Route path="/dogs/:id/edit" element={<EditDogPage />} />
        <Route path="/dogs/:dogId/records/:recordId/edit" element={<EditDailyRecordPage />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;
