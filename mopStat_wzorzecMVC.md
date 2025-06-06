# Spis treści

1. [Opis projektu](#opis-projektu)
2. [Wymagania "MVC wymagania.pdf" — omówienie](#wymagania-mvc-wymaganiapdf--omówienie)
3. [Raport zgodności projektu MopStat (mopsik) z wymaganiami MVC](#raport-zgodności-projektu-mopstat-mopsik-z-wymaganiami-mvc)
4. [Podsumowanie i wnioski](#podsumowanie-i-wnioski)

---

## Opis projektu

### Cel i idea

MopStat to nowoczesna, webowa aplikacja typu SPA (Single Page Application), której głównym zadaniem jest wsparcie właścicieli psów w codziennej opiece nad zwierzętami. Umożliwia wygodne rejestrowanie i monitorowanie kluczowych aktywności każdego pupila, takich jak karmienie, spacery, oddawanie kału, notatki nastroju oraz przyznawanie punktów za opiekę. Dodatkowo posiada dashboard z podsumowaniem oraz eksport danych do pliku CSV. Architektura projektu została oparta w pełni o wzorzec MVC.

Aplikacja powstała jako odpowiedź na realne potrzeby właścicieli zwierząt, a jej głównym celem jest nie tylko organizacja codziennej rutyny, ale także zwiększenie zaangażowania przez elementy grywalizacji, prosty, przyjazny interfejs i zachowanie pełnego bezpieczeństwa danych użytkownika.

### Główne funkcje aplikacji

- Rejestracja oraz logowanie użytkownika (JWT)
- Dodawanie psów do swojego profilu
- Tworzenie wpisów dziennych (karmienie, spacery, kupki, nastrój)
- Podgląd i edycja historii wpisów dla każdego psa
- System punktów za opiekę i dashboard z rankingiem
- Eksport danych do pliku CSV (przez API)
- Ochrona danych – użytkownik widzi tylko swoje psy i wpisy
- Możliwość rozbudowy o kolejne API zewnętrzne, widget pogodowy, tryb mobilny

### Technologie użyte w projekcie

- **Backend:** Spring Boot 3.x (REST API), JPA/Hibernate, H2/SQLite/PostgreSQL, JWT, Maven, Swagger
- **Frontend:** React SPA, axios, react-router-dom, minimalistyczny design pastelowy
- **Testy:** JUnit, MockMvc, testy integracyjne repozytoriów i serwisów
- **Pozostałe:** CORS (integracja localhost), DTO (pełna separacja modelu danych), Swagger UI (interaktywna dokumentacja API)

### Wybór architektury MVC

Projekt od początku budowany był zgodnie z wzorcem Model-View-Controller (MVC), zapewniając pełen rozdział odpowiedzialności:
- Model: logika danych i warstwa dostępu do bazy
- View: prezentacja danych (frontend React)
- Controller: obsługa żądań, logika HTTP, autoryzacja
Podział ten gwarantuje przejrzystość kodu, testowalność, bezpieczeństwo oraz skalowalność i wygodę dalszego rozwoju.

---

## Wymagania "MVC wymagania.pdf" — omówienie

Poniżej szczegółowo przedstawiam progi ocen oraz wymagania stawiane aplikacji zgodnie z dokumentem "MVC wymagania.pdf":

### Ocena 3.0

- Wyraźny podział na warstwy (modele, kontrolery, widoki)
- Projekt musi mieć formę aplikacji internetowej (backend + frontend webowy)
- Komunikacja backend-frontend przez API sieciowe (REST)
- Działanie całości oraz jej elementów musi być poprawne (brak błędów krytycznych)

### Ocena 4.0

- Aplikacja musi korzystać z bazy danych (np. JPA, SQLite)
- Musi istnieć prosty system autentykacji/logowania użytkownika (mogą być „na sztywno” w bazie)

### Ocena 4.5

- Odświeżenie strony nie powoduje wylogowania użytkownika (sesja trwała)

### Ocena 5.0

- Podstrona umożliwiająca dodawanie oraz usuwanie elementów strony (np. postów, wpisów)
- Strona edycji dostępna tylko dla zalogowanych użytkowników

#### Dodatkowe wymagania kluczowe

- Jasna separacja warstw: model/kontroler/widok (separation of concerns)
- Osobny backend i frontend, komunikacja wyłącznie przez REST API
- Autoryzacja, bezpieczeństwo sesji oraz ograniczenie CRUD tylko do zalogowanych użytkowników
- Obsługa błędów HTTP (401/403/400), czytelne komunikaty po stronie API i UI
- Testy poprawności działania, walidacje oraz odporność na typowe błędy użytkownika

---

## Raport zgodności projektu MopStat (mopsik) z wymaganiami MVC

W tej części znajduje się analiza zgodności projektu MopStat z wymaganiami dla poszczególnych progów ocen. Poniżej znajduje się szczegółowa tabela oraz omówienie dla każdej warstwy oraz kluczowych elementów aplikacji.

## Najważniejsze pojęcia — MopStat Backend

W projekcie MopStat zastosowano kluczowe pojęcia nowoczesnych aplikacji webowych, ściśle powiązane ze wzorcem MVC i bezpieczeństwem danych:

### REST API

REST (Representational State Transfer) to styl projektowania komunikacji klient-serwer, w którym backend udostępnia zdefiniowane endpointy HTTP. Wszystkie operacje w MopStat (dodawanie psów, wpisów dziennych, eksport danych) realizowane są poprzez REST API z użyciem metod GET, POST, PUT, DELETE. Komunikacja jest bezstanowa, a dane przesyłane są w formacie JSON.

### JWT (JSON Web Token)

JWT to cyfrowy token wydawany podczas rejestracji/logowania. Każde żądanie do chronionych endpointów musi zawierać token w nagłówku Authorization. Pozwala to na autoryzację i ochronę danych użytkownika – każdy użytkownik widzi i edytuje wyłącznie swoje psy i wpisy. Token przechowywany jest w localStorage po stronie klienta, co umożliwia utrzymanie sesji po odświeżeniu strony.

### DTO (Data Transfer Object)

DTO to dedykowane klasy do transferu danych między backendem i frontendem. Dzięki użyciu DTO aplikacja nie przesyła bezpośrednio encji bazy danych, co zwiększa bezpieczeństwo (brak wycieku wrażliwych pól np. hashów haseł) oraz pozwala kontrolować strukturę odpowiedzi API.

### Separacja warstw

Wzorując się na MVC, kod podzielony jest na warstwy:
- **Model:** klasy JPA, repozytoria, DTO
- **Controller:** klasy obsługujące endpointy REST (np. DogController, RecordController)
- **View:** frontend React, wyświetlający dane użytkownikowi
Dodatkowo w backendzie wydzielone są serwisy (logika biznesowa), konfiguracje bezpieczeństwa oraz testy jednostkowe i integracyjne.

### Bezpieczeństwo i obsługa błędów

Każdy endpoint (poza rejestracją/logowaniem) wymaga poprawnego JWT, a w przypadku braku uprawnień lub niewłaściwego tokena backend zwraca kody błędów 401/403. Wszystkie dane dostępne są tylko dla zalogowanego użytkownika. Logika obsługi wyjątków oraz komunikaty błędów zostały zaimplementowane zarówno po stronie backendu (Spring Boot), jak i frontendowej (React SPA).

---

## Wymagania "MVC wymagania.pdf" — omówienie

Poniżej szczegółowo przedstawiono progi ocen i wymagania stawiane aplikacji zgodnie z dokumentem "MVC wymagania.pdf":

### Ocena 3.0

- Wyraźny podział na warstwy (modele, kontrolery, widoki)
- Projekt musi mieć formę aplikacji internetowej (backend + frontend webowy)
- Komunikacja backend-frontend przez API sieciowe (REST)
- Działanie całości oraz jej elementów musi być poprawne (brak błędów krytycznych)

### Ocena 4.0

- Aplikacja musi korzystać z bazy danych (np. JPA, SQLite)
- Musi istnieć prosty system autentykacji/logowania użytkownika (mogą być „na sztywno” w bazie)

### Ocena 4.5

- Odświeżenie strony nie powoduje wylogowania użytkownika (sesja trwała)

### Ocena 5.0

- Podstrona umożliwiająca dodawanie oraz usuwanie elementów strony (np. postów, wpisów)
- Strona edycji dostępna tylko dla zalogowanych użytkowników

#### Pozostałe wymagania kluczowe

- Jasna separacja warstw: model/kontroler/widok (separation of concerns)
- Osobny backend i frontend, komunikacja wyłącznie przez REST API
- Autoryzacja, bezpieczeństwo sesji oraz ograniczenie CRUD tylko do zalogowanych użytkowników
- Obsługa błędów HTTP (401/403/400), czytelne komunikaty po stronie API i UI
- Testy poprawności działania, walidacje oraz odporność na typowe błędy użytkownika

---

## Raport zgodności projektu MopStat (mopsik) z wymaganiami MVC

Tabela poniżej prezentuje szczegółową analizę zgodności projektu MopStat (branch: mopsik) z wymaganiami „MVC wymagania.pdf” dla każdego progu ocen:

| Progi oceny       | Wymagania                                     | Realizacja w MopStat (branch: mopsik)                      |
|-------------------|-----------------------------------------------|------------------------------------------------------------|
| **3.0**           | - Wyraźny podział na warstwy (MVC) <br> - Web: backend (Spring Boot) + frontend (React SPA) <br> - REST API <br> - Działanie bez crashy | - Struktura: <br>  `/controller`, `/service`, `/repository`, `/dto` <br> - SPA React: podział na strony, komponenty <br> - REST API: przetestowane, brak błędów krytycznych |
| **4.0**           | - Baza danych (JPA/Hibernate) <br> - Logowanie użytkowników | - Baza H2/SQLite/PostgreSQL <br> - Logowanie/rejestracja: JWT |
| **4.5**           | - Sesja trwała po odświeżeniu                 | - JWT w localStorage: użytkownik nie jest wylogowany po odświeżeniu |
| **5.0**           | - Podstrony do dodawania/edycji/usuwania <br> - Edycja tylko dla zalogowanych | - Komponenty: `AddDogPage.jsx`, `EditDogPage.jsx`, `AddRecordPage.jsx`, `EditDailyRecordPage.jsx` <br> - CRUD wyłącznie po autoryzacji (JWT) <br> - Błędy 401/403, bezpieczeństwo danych |

Dla każdej warstwy oraz kluczowych operacji (dodawanie psa, wpisu, eksport, edycja, usuwanie) dostępne są dedykowane komponenty React oraz endpointy backendowe z walidacją i zabezpieczeniem JWT.





## Struktura projektu MopStat (branch: mopsik) — separacja warstw

Projekt MopStat spełnia zasadę ścisłej separacji warstw Model–View–Controller. Poniżej przedstawiono strukturę katalogów i ich odpowiedzialność:

### Backend (Spring Boot)

- `/controller/` – obsługa endpointów REST API (np. DogController, DailyRecordController)
- `/service/` – logika biznesowa, walidacje, bezpieczeństwo
- `/repository/` – operacje CRUD na encjach JPA
- `/dto/` – obiekty transferowe, bezpośrednio przekazywane przez API (brak ujawniania encji bazy)
- `/config/` – konfiguracja bezpieczeństwa JWT, CORS, filtry
- `/exception/` – obsługa wyjątków, klasy do globalnej obsługi błędów
- `/test/` – testy kontrolerów, serwisów, repozytoriów (MockMvc)

#### Przykład podziału kodu backend

```java
@RestController
@RequestMapping("/api/dogs")
public class DogController {
    private final DogService dogService;
    // Konstruktor i endpointy GET/POST/PUT/DELETE...
}

@Service
public class DogService {
    // Logika biznesowa – np. walidacja uprawnień, przypisanie psa do usera
}

public interface DogRepository extends JpaRepository<Dog, Long> {
    // Operacje CRUD z wykorzystaniem JPA
}

public class DogDTO {
    private Long id;
    private String name;
    private String personality;
    private String imagePath;
    // gettery/settery
}
````

### Frontend (React SPA)

* `/src/pages/` – strony aplikacji: logowanie, rejestracja, lista psów, szczegóły psa, dodawanie/edycja wpisów
* `/src/components/` – mniejsze komponenty do dashboardu, formularzy, alertów
* Routing: React Router, ochrona tras przez JWT (localStorage)
* Formularze: walidacja danych, obsługa błędów, komunikaty
* API: komunikacja przez axios/fetch, zawsze z nagłówkiem Authorization: Bearer <JWT>

#### Przykład wywołania API w React

```jsx
await axios.post("http://localhost:8081/api/dogs", {
    name, personality, imagePath
}, {
    headers: { Authorization: `Bearer ${token}` }
});
```

Każda operacja (dodawanie, edycja, usuwanie) obsługiwana jest przez dedykowane komponenty widoku, np. AddDogPage, EditDogPage, AddRecordPage, EditDailyRecordPage.

---

## Checklista spełnienia wymagań na ocenę 5.0 (wg „MVC wymagania.pdf”):

* [x] Wyraźna separacja warstw MVC – Model, Kontroler, Widok w osobnych katalogach
* [x] Projekt jako aplikacja internetowa – backend Spring Boot + frontend React SPA
* [x] Komunikacja wyłącznie przez REST API – brak bezpośredniego powiązania backend-frontend
* [x] Baza danych i operacje CRUD – JPA/Hibernate, tabela Dog, DailyRecord, User, ScoreEntry
* [x] Autentykacja i autoryzacja JWT – każdy endpoint chroniony, user widzi tylko swoje dane
* [x] Sesja trwała (JWT w localStorage) – odświeżenie nie wylogowuje użytkownika
* [x] Dodawanie, edycja, usuwanie – tylko przez podstrony dostępne po zalogowaniu
* [x] DTO w API – nigdy nie są zwracane surowe encje
* [x] Brak błędów krytycznych, czytelna obsługa błędów – testy, MockMvc, walidacje
* [x] Minimalistyczny i pastelowy interfejs SPA – UX zgodny z założeniami
* [x] Testy jednostkowe i integracyjne – pokrycie repozytoriów, serwisów, kontrolerów
* [x] Możliwość eksportu danych i dalszego rozwoju – endpoint CSV, widget pogodowy

---

## Podsumowanie i wnioski

Projekt MopStat (branch: mopsik) spełnia w 100% wymagania na ocenę 5.0 z przedmiotu „Wzorzec MVC w tworzeniu aplikacji internetowych”. Każda warstwa aplikacji została wyraźnie oddzielona, cała komunikacja odbywa się przez REST API, a dane użytkownika są chronione poprzez JWT. Backend jest bezpieczny i zgodny ze współczesnymi standardami Spring Boot, a frontend zapewnia nowoczesny, przyjazny i intuicyjny interfejs użytkownika.

Do głównych atutów projektu należy:

* przejrzystość i łatwość rozbudowy architektury,
* pełne bezpieczeństwo danych,
* zgodność z ideą separation of concerns,
* wzorcowa obsługa błędów i walidacja,
* testy i dokumentacja API (Swagger UI).

Projekt gotowy jest do prezentacji i dalszego rozwoju (np. kolejne integracje API, wersja mobilna, rozbudowa dashboardu).

Repozytorium:
[https://github.com/ledziad/MopStat/tree/mopsik](https://github.com/ledziad/MopStat/tree/mopsik)

---

Opracował: Adam Ledziński
Data: czerwiec 2025

