import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";

export default function DogsPage() {
  const [dogs, setDogs] = useState([]);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  // Sprawdzanie, czy user jest zalogowany
  useEffect(() => {
    const token = localStorage.getItem("jwt");
    if (!token) {
      navigate("/login");
      return;
    }

    // Pobieranie psów użytkownika
    axios
      .get("http://localhost:8081/api/dogs", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setDogs(response.data);
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
    <div style={{ maxWidth: 500, margin: "auto", marginTop: 40 }}>
      <h2>Twoje psy</h2>
      <Link to="/add-dog">
        <button>Dodaj nowego psa</button>
      </Link>
      <ul style={{ marginTop: 20 }}>
        {dogs.length === 0 && <li>Brak psów – dodaj swojego mopsa!</li>}
        {dogs.map((dog) => (
          <li key={dog.id} style={{ marginBottom: 16, borderBottom: "1px solid #ccc" }}>
            <strong>{dog.name}</strong>
            <div>Osobowość: {dog.personality}</div>
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
              <button>Zobacz szczegóły</button>
            </Link>
          </li>
        ))}
      </ul>
      {error && <div style={{ color: "red", marginTop: 10 }}>{error}</div>}
      <button
        style={{ marginTop: 25 }}
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
