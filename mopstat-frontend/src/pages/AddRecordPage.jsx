import { useState } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

export default function AddRecordPage() {
  const { dogId } = useParams();
  const [date, setDate] = useState("");
  const [meals, setMeals] = useState(1);
  const [poops, setPoops] = useState(0);
  const [walks, setWalks] = useState(1);
  const [moodNote, setMoodNote] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const token = localStorage.getItem("jwt");
      await axios.post(`http://localhost:8081/api/dogs/${dogId}/records`, {
        date,
        meals,
        poops,
        walks,
        moodNote,
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        }
      });
      navigate(`/dogs/${dogId}`);
    } catch (err) {
      setError("Błąd dodawania wpisu dziennego!");
    }
  };

  return (
    <div style={{ maxWidth: 400, margin: "auto", marginTop: 60 }}>
      <h2>Dodaj wpis dzienny</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="date"
          value={date}
          onChange={e => setDate(e.target.value)}
          required
        /><br/>
        <input
          type="number"
          min={0}
          placeholder="Ile posiłków?"
          value={meals}
          onChange={e => setMeals(e.target.value)}
        /><br/>
        <input
          type="number"
          min={0}
          placeholder="Ile kupek?"
          value={poops}
          onChange={e => setPoops(e.target.value)}
        /><br/>
        <input
          type="number"
          min={0}
          placeholder="Ile spacerów?"
          value={walks}
          onChange={e => setWalks(e.target.value)}
        /><br/>
        <input
          type="text"
          placeholder="Nastrój/uwagi"
          value={moodNote}
          onChange={e => setMoodNote(e.target.value)}
        /><br/>
        <button type="submit">Dodaj wpis</button>
      </form>
      {error && <div style={{ color: "red", marginTop: 10 }}>{error}</div>}
    </div>
  );
}
