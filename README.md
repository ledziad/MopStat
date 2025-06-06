# MopStat – Aplikacja do codziennego rejestrowania opieki nad psami

Projekt zrealizowany przez:  
Adam Ledziński

## Opis projektu

MopStat to webowa aplikacja, która pozwala użytkownikowi rejestrować karmienia, spacery, kupki i nastroje psów w sposób łatwy, wygodny i nowoczesny.  
Projekt powstał, aby wesprzeć właścicieli oraz fundacje w codziennej organizacji dnia z pupilem – z elementami grywalizacji i lekkim, pastelowym interfejsem.

## Główne funkcje

- Rejestracja/logowanie użytkowników (JWT)
- Dodawanie i edycja psów (własnych, fundacyjnych)
- Wpisy dzienne: karmienie, spacery, kupki, nastroje
- Automatyczna punktacja i dashboard z rankingiem opieki
- Eksport danych do CSV
- Widget pogodowy na dashboardzie (dane z API Open-Meteo)
- Każdy użytkownik widzi wyłącznie swoje dane (pełne bezpieczeństwo)
- Przyjazny, minimalistyczny interfejs (SPA, React)

## Wymagania techniczne

- **Backend:** Java 17+, Spring Boot 3.x, Maven, JPA, Swagger, JWT
- **Frontend:** React 18+, axios, react-router-dom
- **Baza:** H2/SQLite/PostgreSQL (możliwa podmiana)
- **Testy:** JUnit, MockMvc
- **Node.js:** do uruchomienia frontendu

## Jak uruchomić projekt?

1. **Backend**
   - Otwórz folder `src`.
   - `./mvnw spring-boot:run`  
     (lub `mvn spring-boot:run`)
   - Domyślnie uruchamia się na `localhost:8081`.

2. **Frontend**
   - Przejdź do katalogu `mopstat-frontend`.
   - `npm install`
   - `npm run dev`
   - Domyślny port: `localhost:5173`.

3. **Logowanie/rejestracja**
   - Załóż konto przez formularz rejestracyjny (`http://localhost:5173/register`).
   - Zaloguj się, aby korzystać z funkcji aplikacji. (`http://localhost:5173/login`)

4. **Swagger UI**
   - Dokumentacja i testy API dostępne pod:  
     `http://localhost:8081/swagger-ui/index.html`

## Przykładowe konta testowe

- Możesz samodzielnie zarejestrować użytkownika.

## Autor

Adam Ledziński  
mail: [ledzinski.adam@gmail.com]  
Projekt realizowany samodzielnie

## Licencja

Do użytku dydaktycznego.

---

