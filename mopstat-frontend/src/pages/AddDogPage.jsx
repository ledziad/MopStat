import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function AddDogPage() {
  const [name, setName] = useState("");
  const [personality, setPersonality] = useState("");
  const [imagePath, setImagePath] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      // Token z localStorage, jak w innych requestach
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
      navigate("/dogs"); // Po sukcesie wracamy na listę psów
    } catch (err) {
      setError("Błąd dodawania psa! Uzupełnij poprawnie pola.");
    }
  };

  return (
    <div style={{ maxWidth: 400, margin: "auto", marginTop: 80 }}>
      <h2>Dodaj nowego psa</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Imię psa"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        /><br/>
        <input
          type="text"
          placeholder="Osobowość (np. wesoły, leniwy...)"
          value={personality}
          onChange={(e) => setPersonality(e.target.value)}
        /><br/>
        <input
          type="text"
          placeholder="Link do zdjęcia (opcjonalnie)"
          value={imagePath}
          onChange={(e) => setImagePath(e.target.value)}
        /><br/>
        <button type="submit">Dodaj mopsa</button>
      </form>
      {error && <div style={{ color: "red", marginTop: 10 }}>{error}</div>}
    </div>
  );
}
