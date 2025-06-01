import { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";

export default function DogDetailsPage() {
  const { id } = useParams();
  const [dog, setDog] = useState(null);
  const [records, setRecords] = useState([]);
  const [error, setError] = useState("");
  const navigate = useNavigate();

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
            <button
              className="edit-record-btn"
              onClick={() => navigate(`/dogs/${dog.id}/records/${rec.id}/edit`)}
            >
              ✏️ Edytuj
            </button>
          </li>
        ))}
      </ul>
      <button className="dog-details-back-btn" onClick={() => navigate("/dogs")}>
        Wróć do listy psów
      </button>
    </div>
  );
}
