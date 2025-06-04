import { useState, useEffect } from "react";
import axios from "axios";
import { useParams, useNavigate, Link } from "react-router-dom";
import FriendlyAlert from "../components/FriendlyAlert";
export default function EditDailyRecordPage() {
  const { dogId, recordId } = useParams();
  const navigate = useNavigate();
  const [record, setRecord] = useState({
    date: "",
    meals: "",
    poops: "",
    walks: "",
    moodNote: "",
    dogId: dogId
  });
  const [error, setError] = useState(""); // stan błędu
  const [alert, setAlert] = useState(""); // stan alertu sukcesu
  const [loading, setLoading] = useState(true); // stan ładowania

  useEffect(() => {
    const token = localStorage.getItem("jwt");
    axios
      .get(`http://localhost:8081/api/dogs/${dogId}/records`, {
        headers: { Authorization: `Bearer ${token}` }
      })
      .then(res => {
        const found = res.data.find(r => r.id.toString() === recordId);
        if (found) setRecord(found);
        else setError("Nie znaleziono wpisu.");
        setLoading(false);
      })
      .catch(() => {
        setError("Błąd ładowania wpisu dziennego.");
        setLoading(false);
      });
  }, [dogId, recordId]);

  const handleChange = (e) => {
    setRecord({ ...record, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(""); // czyść błąd przed próbą zapisu
    const token = localStorage.getItem("jwt");
    try {
      await axios.put(
        `http://localhost:8081/api/dogs/${dogId}/records/${recordId}`,
        record,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setAlert("Zmiany zapisane! 💾"); // pokaz alert
      setTimeout(() => {
        setAlert("");
        navigate(`/dogs/${dogId}`);
      }, 1200); // przejdź po 1.2 sek
      return;
    } catch (err) {
      setError(
        err.response?.data && typeof err.response.data === "string"
          ? err.response.data
          : "Błąd edycji wpisu."
      );
    }
  };

  if (loading) return <div style={{ textAlign: "center", marginTop: 60 }}>Ładowanie…</div>;

  return (
    <div className="edit-record-page">
      <h2>Edytuj wpis dzienny</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Data:
          <input
            type="date"
            name="date"
            value={record.date}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Ilość posiłków:
          <input
            type="number"
            name="meals"
            value={record.meals}
            onChange={handleChange}
            min={0} max={10}
            placeholder="np. 2"
            required
          />
        </label>
        <label>
          Kupki:
          <input
            type="number"
            name="poops"
            value={record.poops}
            onChange={handleChange}
            min={0} max={10}
            placeholder="np. 1"
            required
          />
        </label>
        <label>
          Spacery:
          <input
            type="number"
            name="walks"
            value={record.walks}
            onChange={handleChange}
            min={0} max={10}
            placeholder="np. 2"
            required
          />
        </label>
        <label>
          Notatka / Nastrój:
          <textarea
            name="moodNote"
            value={record.moodNote}
            onChange={handleChange}
            placeholder="np. wesoły, energiczny, śpioch"
            rows={2}
            className="edit-textarea"
          />
        </label>
        <div className="form-buttons">
          <button className="save-btn" type="submit">Zapisz zmiany</button>
          <Link to={`/dogs/${dogId}`}>
            <button className="cancel-btn" type="button">Anuluj</button>
          </Link>
        </div>
      </form>
      {/* FriendlyAlert dla sukcesu */}
      {alert && (
        <FriendlyAlert
          message={alert}
          type="success"
          onClose={() => setAlert("")}
        />
      )}
      {/* FriendlyAlert dla błędu */}
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
