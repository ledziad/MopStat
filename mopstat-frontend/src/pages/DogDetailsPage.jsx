import { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import FriendlyAlert from "../components/FriendlyAlert";

export default function DogDetailsPage() {
  const { id } = useParams();
  const [dog, setDog] = useState(null);
  const [records, setRecords] = useState([]);
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const [alert, setAlert] = useState("");

  useEffect(() => {
    const token = localStorage.getItem("jwt");
    if (!token) {
      navigate("/login");
      return;
    }
    axios.get(`http://localhost:8081/api/dogs/${id}`, {
      headers: { Authorization: `Bearer ${token}` }
    }).then((res) => setDog(res.data))
      .catch(() => setError("Nie można pobrać psa"));

    axios.get(`http://localhost:8081/api/dogs/${id}/records`, {
      headers: { Authorization: `Bearer ${token}` }
    }).then((res) => setRecords(res.data))
      .catch(() => setError("Nie można pobrać wpisów dziennych"));
  }, [id, navigate]);

const handleDeleteDog = async () => {
    if (!window.confirm("Na pewno chcesz usunąć tego mopsa?")) return;
    try {
      const token = localStorage.getItem("jwt");
      await axios.delete(`http://localhost:8081/api/dogs/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setAlert("Mops usunięty! 🐾");
      setTimeout(() => {
        setAlert("");
        navigate("/dogs");
      }, 1200);
    } catch (err) {
      setError("Błąd podczas usuwania mopsa!");
    }
  };

const handleDeleteRecord = async (recordId) => {
    if (!window.confirm("Na pewno chcesz usunąć ten wpis dzienny?")) return;
    try {
      const token = localStorage.getItem("jwt");
      await axios.delete(
        `http://localhost:8081/api/dogs/${id}/records/${recordId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setAlert("Wpis dzienny usunięty! ✂️");
      setRecords(records.filter(rec => rec.id !== recordId)); // odśwież listę
    } catch (err) {
      setError("Błąd usuwania wpisu dziennego!");
    }
  };

  if (error) return <div className="dog-details-error">{error}</div>;
  if (!dog) return <div>Ładowanie...</div>;

  return (
    <div className="dog-details-page">
      <h2>{dog.name}</h2>
      <div className="dog-details-personality">
        Osobowość: {dog.personality}
      </div>
      {dog.imagePath && (
        <img
          src={dog.imagePath}
          alt={dog.name}
          className="dog-details-image"
        />
      )}
      <h3>Wpisy dzienne</h3>
      <button onClick={() => navigate(`/add-record/${dog.id}`)}>
        Dodaj wpis dzienny
      </button>
      <ul className="daily-record-list">
        {records.length === 0 && <li>Brak wpisów dziennych.</li>}
        {records.map(rec => (
          <li key={rec.id}>
            <b>{rec.date}:</b> spacery: {rec.walks}, kupki: {rec.poops}, posiłki: {rec.meals}, nastrój: {rec.moodNote}
            <div className="record-actions-row">
              <button
                className="edit-record-btn"
                onClick={() => navigate(`/dogs/${dog.id}/records/${rec.id}/edit`)}
              >
                ✏️ Edytuj
              </button>
              <button
                className="delete-record-btn"
                onClick={() => handleDeleteRecord(rec.id)}
              >
                Usuń
              </button>
            </div>
          </li>
        ))}


      </ul>
      <button className="dog-details-back-btn" onClick={() => navigate("/dogs")}>
        Wróć do listy psów
      </button>
      <button
        className="delete-dog-btn"
        onClick={handleDeleteDog}
      >
        🗑️ Usuń mopsa
      </button>
      {alert && (
        <FriendlyAlert
          message={alert}
          type="success"
          onClose={() => setAlert("")}
        />
      )}
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
