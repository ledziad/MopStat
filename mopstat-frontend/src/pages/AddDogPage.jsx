import { useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import FriendlyAlert from "../components/FriendlyAlert";
export default function AddDogPage() {
  const [name, setName] = useState("");
  const [personality, setPersonality] = useState("");
  const [imagePath, setImagePath] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const [alert, setAlert] = useState("");


  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const token = localStorage.getItem("jwt");
      await axios.post("http://localhost:8081/api/dogs", {
        name,
        personality,
        imagePath,
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        }
      });

      // [FRIENDLY ALERT] — tu zamiast nawigacji od razu, najpierw pokaz alert:
      setAlert("Dodano mopsa! 🐶");
      setTimeout(() => {
        setAlert("");      // schowaj alert po 1.2 sekundy
        navigate("/dogs"); // przejdź po krótkim opóźnieniu
      }, 1200);
      return;

    } catch (err) {
      setError("Błąd dodawania psa! Uzupełnij poprawnie pola.");
    }
  };

  return (
    <div className="add-dog-page">
      <h2>Dodaj nowego psa</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Imię psa"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Osobowość (np. wesoły, leniwy...)"
          value={personality}
          onChange={(e) => setPersonality(e.target.value)}
        />
        <input
          type="text"
          placeholder="Link do zdjęcia (opcjonalnie)"
          value={imagePath}
          onChange={(e) => setImagePath(e.target.value)}
        />
        <button type="submit">Dodaj mopsa</button>
        <Link to="/dogs">
          <button type="button">Anuluj</button>
        </Link>
      </form>
       {/* [FRIENDLY ALERT] — wyświetl alert sukcesu (na wierzchu, w pastelowych kolorach) */}
            {alert && (
              <FriendlyAlert
                message={alert}
                type="success"
                onClose={() => setAlert("")}
              />
            )}
            {/* [FRIENDLY ALERT] — opcjonalnie możesz użyć FriendlyAlert także do error: */}
            {error && (
              <FriendlyAlert
                message={error}
                type="error"
                onClose={() => setError("")}
              />
            )}
          </div>
  );
}
