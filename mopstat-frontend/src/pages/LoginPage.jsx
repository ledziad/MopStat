import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const response = await axios.post("http://localhost:8081/api/auth/login", {
        username,
        password,
      });
      localStorage.setItem("jwt", response.data.token);
      navigate("/dogs");
    } catch (err) {
      setError("Nieprawidłowe dane logowania!");
    }
  };

  return (
    <div style={{ maxWidth: 350, margin: "auto", marginTop: 100 }}>
      <h2>Logowanie MopStat</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Nazwa użytkownika"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        /><br/>
        <input
          type="password"
          placeholder="Hasło"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        /><br/>
        <button type="submit">Zaloguj</button>
      </form>
      {error && <div style={{ color: "red", marginTop: 10 }}>{error}</div>}

    <div style={{ marginTop: 10 }}>
      <Link to="/register">Nie masz konta? Zarejestruj się</Link>
    </div>
    </div>
  );
}
