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

    // Pobierz psy użytkownika
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
    <div style={{ maxWidth: 520, margin: "auto", marginTop: 40, background: "#f9f5ef", borderRadius: 24, boxShadow: "0 2px 12px #e6e0d6", padding: "2em" }}>
      <h2 style={{ color: "#604c32" }}>Twoje psy</h2>
      <button
        style={{
          background: "linear-gradient(90deg, #d7c7ae, #b7d3b3)",
          color: "#3d2c16",
          border: "none",
          borderRadius: 12,
          padding: "0.7em 1.6em",
          fontWeight: "bold",
          marginBottom: 24,
          boxShadow: "0 2px 10px #ececec",
          cursor: "pointer"
        }}
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

      {/* Kafelki mopsowe */}
      <div style={{
        display: "flex",
        justifyContent: "center",
        gap: 24,
        margin: "24px 0"
      }}>
        <div style={{ background: "#fffbe7", borderRadius: 16, padding: 20, boxShadow: "0 2px 10px #ececec" }}>
          <div style={{ fontSize: "2.1rem" }}>🍖</div>
          <div>Karmień: <b>{totals.meals}</b></div>
        </div>
        <div style={{ background: "#eafcf5", borderRadius: 16, padding: 20, boxShadow: "0 2px 10px #ececec" }}>
          <div style={{ fontSize: "2.1rem" }}>💩</div>
          <div>Kupek: <b>{totals.poops}</b></div>
        </div>
        <div style={{ background: "#f5ecfc", borderRadius: 16, padding: 20, boxShadow: "0 2px 10px #ececec" }}>
          <div style={{ fontSize: "2.1rem" }}>🚶</div>
          <div>Spacerów: <b>{totals.walks}</b></div>
        </div>
        <div style={{ background: "#fcf0ec", borderRadius: 16, padding: 20, boxShadow: "0 2px 10px #ececec" }}>
          <div style={{ fontSize: "2.1rem" }}>😄</div>
          <div>Nastroje: <b>{totals.moods}</b></div>
        </div>
      </div>

      <Link to="/add-dog">
        <button style={{
          marginBottom: 24,
          background: "#b7d3b3",
          color: "#3d2c16",
          border: "none",
          borderRadius: 12,
          padding: "0.7em 1.4em",
          fontWeight: "bold",
          boxShadow: "0 2px 6px #e3e3db",
          cursor: "pointer"
        }}>
          ➕ Dodaj nowego psa
        </button>
      </Link>
      <ul style={{ marginTop: 20, padding: 0 }}>
        {dogs.length === 0 && <li>Brak psów – dodaj swojego mopsa!</li>}
        {dogs.map((dog) => (
          <li key={dog.id} style={{
            marginBottom: 16,
            borderBottom: "1px solid #ede6d7",
            paddingBottom: 10,
            listStyle: "none"
          }}>
            <strong style={{ color: "#6d573b" }}>{dog.name}</strong>
            <div style={{ color: "#3d2c16" }}>Osobowość: {dog.personality}</div>
            {dog.imagePath && (
              <img
                src={dog.imagePath}
                alt={dog.name}
                width={80}
                height={80}
                style={{ objectFit: "cover", borderRadius: 10, marginTop: 8 }}
              />
            )}
            <br />
            <Link to={`/dogs/${dog.id}`}>
              <button style={{
                marginRight: 12,
                background: "#f3e7d5",
                color: "#5e4429",
                border: "none",
                borderRadius: 10,
                padding: "0.5em 1em",
                cursor: "pointer"
              }}>
                Zobacz szczegóły
              </button>
            </Link>
            {/* Przyciski pastelowe */}
            <Link to={`/dogs/${dog.id}/edit`}>
              <button style={{
                background: "#b7d3b3",
                color: "#3d2c16",
                border: "none",
                borderRadius: 10,
                padding: "0.5em 1em",
                cursor: "pointer"
              }}>
                ✏️ Edytuj
              </button>
            </Link>
          </li>
        ))}
      </ul>
      {error && <div style={{ color: "crimson", marginTop: 10 }}>{error}</div>}
      <button
        style={{
          marginTop: 25,
          background: "#e1d6c0",
          color: "#604c32",
          border: "none",
          borderRadius: 12,
          padding: "0.6em 1.4em",
          fontWeight: "bold",
          cursor: "pointer"
        }}
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
