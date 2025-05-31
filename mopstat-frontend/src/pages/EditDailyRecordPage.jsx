import { useState, useEffect } from "react";
import axios from "axios";
import { useParams, useNavigate, Link } from "react-router-dom";

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
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

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
    const token = localStorage.getItem("jwt");
    try {
      await axios.put(
        `http://localhost:8081/api/dogs/${dogId}/records/${recordId}`,
        record,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      navigate(`/dogs/${dogId}`);
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
    <div style={{
      maxWidth: 420,
      margin: "auto",
      marginTop: 40,
      background: "#f9f5ef",
      borderRadius: 24,
      boxShadow: "0 2px 12px #e6e0d6",
      padding: "2.5em"
    }}>
      <h2 style={{ color: "#604c32", marginBottom: 16 }}>Edytuj wpis dzienny</h2>
      <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: 18 }}>
        <label>
          Data:
          <input
            type="date"
            name="date"
            value={record.date}
            onChange={handleChange}
            style={{
              borderRadius: 12,
              border: "1px solid #e3e3db",
              padding: "0.55em",
              background: "#f8f3ea",
              marginTop: 6
            }}
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
            style={{
              borderRadius: 12,
              border: "1px solid #e3e3db",
              padding: "0.55em",
              background: "#f8f3ea",
              marginTop: 6
            }}
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
            style={{
              borderRadius: 12,
              border: "1px solid #e3e3db",
              padding: "0.55em",
              background: "#f8f3ea",
              marginTop: 6
            }}
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
            style={{
              borderRadius: 12,
              border: "1px solid #e3e3db",
              padding: "0.55em",
              background: "#f8f3ea",
              marginTop: 6
            }}
            required
          />
        </label>
        <label>
          Notatka / Nastrój:
          <input
            name="moodNote"
            value={record.moodNote}
            onChange={handleChange}
            placeholder="np. wesoły, energiczny, śpioch"
            style={{
              borderRadius: 12,
              border: "1px solid #e3e3db",
              padding: "0.55em",
              background: "#f8f3ea",
              marginTop: 6
            }}
          />
        </label>
        <div style={{ display: "flex", gap: 12, marginTop: 10 }}>
          <button
            type="submit"
            style={{
              background: "linear-gradient(90deg, #d7c7ae, #b7d3b3)",
              color: "#3d2c16",
              border: "none",
              borderRadius: 12,
              padding: "0.7em 1.6em",
              fontWeight: "bold",
              cursor: "pointer"
            }}>
            Zapisz zmiany
          </button>
          <Link to={`/dogs/${dogId}`} style={{ textDecoration: "none" }}>
            <button type="button" style={{
              background: "#eee4d4",
              color: "#604c32",
              border: "none",
              borderRadius: 12,
              padding: "0.7em 1.6em",
              cursor: "pointer"
            }}>
              Anuluj
            </button>
          </Link>
        </div>
        {error && <div style={{ color: "crimson", marginTop: 8 }}>{error}</div>}
      </form>
    </div>
  );
}
