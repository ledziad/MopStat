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
    // Pobierz szczegóły psa
    axios.get(`http://localhost:8081/api/dogs/${id}`, {
      headers: { Authorization: `Bearer ${token}` }
    }).then((res) => setDog(res.data))
      .catch(() => setError("Nie można pobrać psa"));

    // Pobierz wpisy dzienne psa
    axios.get(`http://localhost:8081/api/dogs/${id}/records`, {
      headers: { Authorization: `Bearer ${token}` }
    }).then((res) => setRecords(res.data))
      .catch(() => setError("Nie można pobrać wpisów dziennych"));
  }, [id, navigate]);

  if (error) return <div style={{ color: "red" }}>{error}</div>;
  if (!dog) return <div>Ładowanie...</div>;

  return (
    <div style={{ maxWidth: 600, margin: "auto", marginTop: 40 }}>
      <h2>{dog.name}</h2>
      <div>Osobowość: {dog.personality}</div>
      {dog.imagePath && <img src={dog.imagePath} alt={dog.name} width={120} style={{ margin: 10, borderRadius: 10 }} />}
      <h3>Wpisy dzienne</h3>
      <button onClick={() => navigate(`/add-record/${dog.id}`)}>Dodaj wpis dzienny</button>
    <ul>
      {records.length === 0 && <li>Brak wpisów dziennych.</li>}
      {records.map(rec => (
        <li key={rec.id} style={{ marginBottom: 14, borderBottom: "1px solid #eee", paddingBottom: 6 }}>
          <b>{rec.date}:</b> spacery: {rec.walks}, kupki: {rec.poops}, posiłki: {rec.meals}, nastrój: {rec.moodNote}
          <button
            style={{
              marginLeft: 12,
              background: "#b7d3b3",
              color: "#3d2c16",
              border: "none",
              borderRadius: 8,
              padding: "0.25em 0.9em",
              cursor: "pointer"
            }}
            onClick={() => navigate(`/dogs/${dog.id}/records/${rec.id}/edit`)}
          >
            ✏️ Edytuj
          </button>
        </li>
      ))}
    </ul>

      <button style={{ marginTop: 16 }} onClick={() => navigate("/dogs")}>Wróć do listy psów</button>
    </div>
  );
}
