import { useState, useEffect } from "react";
import axios from "axios";
import { useParams, useNavigate, Link } from "react-router-dom";

export default function EditDogPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [dog, setDog] = useState({ name: "", personality: "", imagePath: "" });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem("jwt");
    axios
      .get(`http://localhost:8081/api/dogs/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      })
      .then(res => {
        setDog(res.data);
        setLoading(false);
      })
      .catch(() => {
        setError("Błąd ładowania mopsa.");
        setLoading(false);
      });
  }, [id]);

  const handleChange = (e) => {
    setDog({ ...dog, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("jwt");
    try {
      await axios.put(`http://localhost:8081/api/dogs/${id}`, dog, {
        headers: { Authorization: `Bearer ${token}` }
      });
      navigate("/"); // Po edycji wraca do listy psów
    } catch (err) {
      setError(
        err.response?.data && typeof err.response.data === "string"
          ? err.response.data
          : "Błąd edycji mopsa."
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
      <h2 style={{ color: "#604c32", marginBottom: 16 }}>Edytuj mopsa</h2>
      <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: 18 }}>
        <label>
          Imię mopsa:
          <input
            name="name"
            value={dog.name}
            onChange={handleChange}
            placeholder="Podaj imię mopsa"
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
          Osobowość:
          <input
            name="personality"
            value={dog.personality}
            onChange={handleChange}
            placeholder="Napisz, jaki jest Twój mops"
            style={{
              borderRadius: 12,
              border: "1px solid #e3e3db",
              padding: "0.55em",
              background: "#f8f3ea",
              marginTop: 6
            }}
          />
        </label>
        <label>
          Zdjęcie (URL):
          <input
            name="imagePath"
            value={dog.imagePath}
            onChange={handleChange}
            placeholder="Wklej link do zdjęcia mopsa"
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
              background: "#e3eed2",
              color: "#3d2c16",
              border: "none",
              borderRadius: 12,
              padding: "0.7em 1.6em",
              fontWeight: "bold",
              cursor: "pointer"
            }}>
            Zapisz zmiany
          </button>
          <Link to="/dogs" style={{ textDecoration: "none" }}>
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
