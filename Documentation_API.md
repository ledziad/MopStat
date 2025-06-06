```markdown
## 📑 Dokumentacja API MopStat

### Autoryzacja

Po zalogowaniu otrzymujesz **token JWT**.  
Każde kolejne żądanie do endpointów (poza rejestracją i logowaniem) wymaga tokena.  
W Swagger UI kliknij „Authorize” (kłódka) i wklej token.  
W innych narzędziach (np. Postman, fetch) dodaj nagłówek:
```

Authorization: Bearer <token>

````

---

### ENDPOINTY

| Metoda | Endpoint             | Opis                                       | Wymaga JWT? |
|--------|----------------------|---------------------------------------------|:-----------:|
| POST   | `/api/auth/register` | Rejestracja nowego użytkownika              |     ❌      |
| POST   | `/api/auth/login`    | Logowanie, zwraca token JWT                 |     ❌      |
| GET    | `/api/dogs`          | Lista psów zalogowanego użytkownika         |     ✅      |
| POST   | `/api/dogs`          | Dodaj nowego psa                           |     ✅      |
| GET    | `/api/records`       | Lista wpisów dziennych zalogowanego usera   |     ✅      |
| POST   | `/api/records`       | Dodaj wpis dzienny                         |     ✅      |
| GET    | `/api/csv`           | Eksport danych do CSV                      |     ✅      |

---

### Przykładowe zapytania

**Rejestracja**
```json
{
  "username": "adam",
  "password": "tajnehaslo"
}
````

**Logowanie**

```json
{
  "username": "adam",
  "password": "tajnehaslo"
}
```

*Response:*

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Dodanie psa**

```json
{
  "name": "Faworek",
  "personality": "Rozrabiaka",
  "imagePath": "/images/fawor.jpg"
}
```

**Dodanie wpisu dziennego**

```json
{
  "date": "2025-05-20",
  "meals": 2,
  "poops": 1,
  "walks": 2,
  "moodNote": "Mega energiczny",
  "dogId": 1
}
```

---

### Kody odpowiedzi

* `401 Unauthorized` – brak lub nieprawidłowy token JWT
* `403 Forbidden` – brak dostępu do danych (nie Twoje psy/wpisy)
* `404 Not Found` – nie znaleziono psa/wpisu
* `400 Bad Request` – niepoprawne dane wejściowe

---

### Swagger UI

Pełna interaktywna dokumentacja pod adresem:
`http://localhost:8080/swagger-ui/index.html`

```
```
