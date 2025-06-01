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
      navigate("/dogs");
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
    <div className="edit-dog-page">
      <h2>Edytuj mopsa</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Imię mopsa:
          <input
            name="name"
            value={dog.name}
            onChange={handleChange}
            placeholder="Podaj imię mopsa"
            required
            type="text"
          />
        </label>
        <label>
          Osobowość:
          <input
            name="personality"
            value={dog.personality}
            onChange={handleChange}
            placeholder="Napisz, jaki jest Twój mops"
            type="text"
          />
        </label>
        <label>
          Zdjęcie (URL):
          <input
            name="imagePath"
            value={dog.imagePath}
            onChange={handleChange}
            placeholder="Wklej link do zdjęcia mopsa"
            type="text"
          />
        </label>
        <div className="form-buttons">
          <button type="submit" className="save-btn">
            Zapisz zmiany
          </button>
          <Link to="/dogs">
            <button type="button" className="cancel-btn">
              Anuluj
            </button>
          </Link>
        </div>
        {error && <div className="error">{error}</div>}
      </form>
    </div>
  );
}
