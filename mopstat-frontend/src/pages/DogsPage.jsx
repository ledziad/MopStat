import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, Cell } from 'recharts';

export default function DogsPage() {
  const [dogs, setDogs] = useState([]);
  const [error, setError] = useState("");
  const [totals, setTotals] = useState({ meals: 0, poops: 0, walks: 0, moods: 0 });
  const [scores, setScores] = useState({});
  const [totalScore, setTotalScore] = useState(0);
  const [totalRecords, setTotalRecords] = useState(0);
  const [topDog, setTopDog] = useState(null);
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
        let meals = 0, poops = 0, walks = 0, moods = 0, records = 0;
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
            records += recRes.data.length;
          })
        );
        setTotals({ meals, poops, walks, moods });
        setTotalRecords(records);

        // Dashboard punktów – pobierz score dla każdego psa
        let scoresObj = {};
        let sum = 0;
        let bestScore = -1;
        let bestDog = null;
        await Promise.all(
          response.data.map(async (dog) => {
            try {
              const res = await axios.get(
                `http://localhost:8081/api/dogs/${dog.id}/score`,
                { headers: { Authorization: `Bearer ${token}` } }
              );
              const score = res.data?.score ?? 0;
              scoresObj[dog.id] = score;
              sum += score;
              if (score > bestScore) {
                bestScore = score;
                bestDog = dog;
              }
            } catch {
              scoresObj[dog.id] = 0;
            }
          })
        );
        setScores(scoresObj);
        setTotalScore(sum);
        setTopDog(bestDog);
      })
      .catch((err) => {
        setError("Błąd pobierania psów! Zaloguj się ponownie.");
        if (err.response && (err.response.status === 401 || err.response.status === 403)) {
          localStorage.removeItem("jwt");
          navigate("/login");
        }
      });
  }, [navigate]);

  // Przygotowanie danych do wykresu
  const chartData = dogs.map(dog => ({
    name: dog.name,
    score: scores[dog.id] ?? 0,
  }));
  const pastelColors = ['#d7c7ae', '#b7d3b3', '#f5ecfc', '#fbe6d5', '#eafcf5', '#fcf0ec'];

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

      <div className="dashboard-mops">
        <div className="dashboard-tile">
          <div className="dashboard-label">Suma punktów:</div>
          <div className="dashboard-value">{totalScore}</div>
        </div>
        <div className="dashboard-tile">
          <div className="dashboard-label">Liczba wpisów:</div>
          <div className="dashboard-value">{totalRecords}</div>
        </div>
        <div className="dashboard-tile">
          <div className="dashboard-label">Top mops:</div>
          <div className="dashboard-value">
            {topDog ? (
              <>
                <b>{topDog.name}</b>
                {scores[topDog.id] > 0 && (
                  <> ({scores[topDog.id]} pkt)</>
                )}
              </>
            ) : (
              <>brak</>
            )}
          </div>
        </div>
      </div>

      <div className="chart-container">
        <h3 className="chart-title">Ranking punktów — Twoje psy</h3>
        <ResponsiveContainer width="100%" height="85%">
          <BarChart data={chartData}>
            <XAxis dataKey="name" tick={{ fontSize: 14, fill: '#8d765a' }} />
            <YAxis allowDecimals={false} tick={{ fontSize: 13, fill: '#b8a68d' }} />
            <Tooltip />
            <Bar dataKey="score">
              {chartData.map((entry, idx) => (
                <Cell key={entry.name} fill={pastelColors[idx % pastelColors.length]} />
              ))}
            </Bar>
          </BarChart>
        </ResponsiveContainer>
      </div>

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
