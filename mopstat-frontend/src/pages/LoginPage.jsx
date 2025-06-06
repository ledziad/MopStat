import { useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";

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
    <div className="login-page">
      <h2>Logowanie MopStat</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Nazwa użytkownika"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Hasło"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">Zaloguj</button>
      </form>
      {error && <div className="error">{error}</div>}

      <div className="switch-link">
        <Link to="/register">Nie masz konta? Zarejestruj się</Link>
      </div>
    </div>
  );
}
