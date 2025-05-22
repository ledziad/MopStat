import { useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";

export default function RegisterPage() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess(false);
    try {
      await axios.post("http://localhost:8081/api/auth/register", {
        username,
        email,
        password,
      });
      setSuccess(true);
      setTimeout(() => navigate("/login"), 1500);
    } catch (err) {
      setError("Błąd rejestracji! Spróbuj inną nazwę lub email.");
    }
  };

  return (
    <div style={{ maxWidth: 350, margin: "auto", marginTop: 100 }}>
      <h2>Rejestracja MopStat</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Nazwa użytkownika"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        /><br/>
        <input
          type="email"
          placeholder="E-mail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        /><br/>
        <input
          type="password"
          placeholder="Hasło"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        /><br/>
        <button type="submit">Zarejestruj</button>
      </form>
      {error && <div style={{ color: "red", marginTop: 10 }}>{error}</div>}
      {success && <div style={{ color: "green", marginTop: 10 }}>Rejestracja udana! Przekierowanie...</div>}
      <div style={{ marginTop: 10 }}>
        <Link to="/login">Masz już konto? Zaloguj się</Link>
      </div>
    </div>
  );
}
