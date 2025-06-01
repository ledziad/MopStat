import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";

export default function DogsPage() {
  const [dogs, setDogs] = useState([]);
  const [error, setError] = useState("");
  const [totals, setTotals] = useState({ meals: 0, poops: 0, walks: 0, moods: 0 });
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("jwt");
    if (!token) {
      navigate("/login");
      return;
    }
    axios
      .get("http://localhost:8081/api/dogs", {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(async (response) => {
        setDogs(response.data);
        // Pobierz wpisy dzienne każdego psa
        let meals = 0, poops = 0, walks = 0, moods = 0;
        await Promise.all(
          response.data.map(async (dog) => {
            const recRes = await axios.get(
              `http://localhost:8081/api/dogs/${dog.id}/records`,
              { headers: { Authorization: `Bearer ${token}` } }
            );
            recRes.data.forEach((rec) => {
              meals += rec.meals || 0;
              poops += rec.poops || 0;
              walks += rec.walks || 0;
              moods += rec.moodNote && rec.moodNote.trim() ? 1 : 0;
            });
          })
        );
        setTotals({ meals, poops, walks, moods });
      })
      .catch((err) => {
        setError("Błąd pobierania psów! Zaloguj się ponownie.");
        if (err.response && (err.response.status === 401 || err.response.status === 403)) {
          localStorage.removeItem("jwt");
          navigate("/login");
        }
      });
  }, [navigate]);

  return (
    <div className="dogs-page">
      <h2>Twoje psy</h2>
      <button
        className="export-btn"
        onClick={async () => {
          const token = localStorage.getItem("jwt");
          try {
            const res = await fetch("http://localhost:8081/api/export/csv", {
              headers: { Authorization: `Bearer ${token}` }
            });
            if (!res.ok) throw new Error("Błąd pobierania CSV");
            const blob = await res.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement("a");
            a.href = url;
            a.download = "mopstat-eksport.csv";
            document.body.appendChild(a);
            a.click();
            a.remove();
            window.URL.revokeObjectURL(url);
          } catch {
            alert("Nie udało się pobrać CSV! Spróbuj ponownie.");
          }
        }}
      >
        📥 Pobierz CSV
      </button>
      <div className="summary-row">
        <div className="summary-tile meals">
          <div>🍖</div>
          <div>Karmień: <b>{totals.meals}</b></div>
        </div>
        <div className="summary-tile poops">
          <div>💩</div>
          <div>Kupek: <b>{totals.poops}</b></div>
        </div>
        <div className="summary-tile walks">
          <div>🚶</div>
          <div>Spacerów: <b>{totals.walks}</b></div>
        </div>
        <div className="summary-tile moods">
          <div>😄</div>
          <div>Nastroje: <b>{totals.moods}</b></div>
        </div>
      </div>
      <Link to="/add-dog">
        <button className="add-btn">
          ➕ Dodaj nowego psa
        </button>
      </Link>
      <ul className="dog-list">
        {dogs.length === 0 && <li>Brak psów – dodaj swojego mopsa!</li>}
        {dogs.map((dog) => (
          <li key={dog.id}>
            <div className="dog-name">{dog.name}</div>
            <div className="dog-personality">Osobowość: {dog.personality}</div>
            {dog.imagePath && (
              <img
                src={dog.imagePath}
                alt={dog.name}
                className="dog-img"
              />
            )}
            <br />
            <Link to={`/dogs/${dog.id}`}>
              <button className="details-btn">
                Zobacz szczegóły
              </button>
            </Link>
            <Link to={`/dogs/${dog.id}/edit`}>
              <button className="edit-btn">
                ✏️ Edytuj
              </button>
            </Link>
          </li>
        ))}
      </ul>
      {error && <div className="error">{error}</div>}
      <button
        className="logout-btn"
        onClick={() => {
          localStorage.removeItem("jwt");
          navigate("/login");
        }}
      >
        Wyloguj
      </button>
    </div>
  );
}
