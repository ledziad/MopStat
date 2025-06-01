import { useState } from "react";
import axios from "axios";
import { Link, useNavigate, useParams } from "react-router-dom";

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
    <div className="add-record-page">
      <h2>Dodaj wpis dzienny</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="date"
          value={date}
          onChange={e => setDate(e.target.value)}
          required
        />
        <input
          type="number"
          min={0}
          placeholder="Ile posiłków?"
          value={meals}
          onChange={e => setMeals(e.target.value)}
        />
        <input
          type="number"
          min={0}
          placeholder="Ile kupek?"
          value={poops}
          onChange={e => setPoops(e.target.value)}
        />
        <input
          type="number"
          min={0}
          placeholder="Ile spacerów?"
          value={walks}
          onChange={e => setWalks(e.target.value)}
        />
        <input
          type="text"
          placeholder="Nastrój/uwagi"
          value={moodNote}
          onChange={e => setMoodNote(e.target.value)}
        />
        <button type="submit">Dodaj wpis</button>
        <Link to={`/dogs/${dogId}`}>
                  <button type="button">Anuluj</button>
                </Link>
      </form>
      {error && <div className="error">{error}</div>}
    </div>
  );
}
