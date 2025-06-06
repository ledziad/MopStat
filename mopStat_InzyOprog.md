# Spis treści

1. [Opis projektu](#opis-projektu)
2. [Najważniejsze pojęcia — MopStat Backend](#najważniejsze-pojęcia--mopstat-backend)
3. [Architektura projektu (backend-i-frontend)](#architektura-projektu-backend-i-frontend)
4. [Raport zgodności z wymaganiami](#raport-zgodności-z-wymaganiami)
5. [Założenia testowe](#założenia-testowe)
6. [Zrzuty ekranu](#zrzuty-ekranu)
7. [Bezpieczeństwo i JWT](#bezpieczeństwo-i-jwt)
8. [Możliwości rozbudowy (propozycje)](#możliwości-rozbudowy-propozycje)
9. [Wnioski końcowe](#wnioski-końcowe)

---

## Opis projektu

„MopStat” to lekka aplikacja webowa wspierająca codzienną opiekę nad psami — karmienie, spacery, monitorowanie nastroju.  
Projekt powstał, by wesprzeć właścicieli, fundacje i weterynarzy w lepszej organizacji dnia, z elementami grywalizacji oraz minimalistycznym, pastelowym interfejsem.

**Główne cele aplikacji:**
- Rejestracja i historia opieki nad psami
- Motywacja przez system punktów i dashboard
- Bezpieczeństwo (JWT)
- Wersja MVP z czytelnym interfejsem (React SPA + Spring Boot REST API)

---

## Najważniejsze pojęcia — MopStat Backend

- **REST API**  
  Styl projektowania komunikacji przez HTTP (GET/POST/PUT/DELETE) – każda operacja na danych przechodzi przez dedykowany endpoint (`/api/dogs`, `/api/auth`, `/api/score/summary` itd.).

- **JWT (JSON Web Token)**  
  Token autoryzacji otrzymywany po zalogowaniu, przesyłany w nagłówku `Authorization`, chroni wszystkie endpointy poza rejestracją i logowaniem.

- **CORS**  
  Mechanizm bezpieczeństwa przeglądarki, pozwalający na połączenia między backendem (np. localhost:8081) a frontendem (localhost:5173).

- **DTO (Data Transfer Object)**  
  Klasa używana do bezpiecznego przesyłania danych pomiędzy backendem i frontendem — nigdy nie udostępniamy bezpośrednio encji z bazy.

- **Swagger / OpenAPI**  
  Automatyczna, interaktywna dokumentacja REST API, umożliwiająca testowanie endpointów przez przeglądarkę.

- **Repozytorium JPA / Encja JPA**  
  Encja = klasa reprezentująca tabelę w bazie (`@Entity`). Repozytorium JPA – interfejs do operacji CRUD bez pisania SQL.

- **Serwis (Service)**  
  Warstwa logiki biznesowej (np. walidacja, wyliczenia punktów, bezpieczeństwo).

- **SecurityConfig / Filtr JWT**  
  Konfiguracja bezpieczeństwa Spring Security, wymuszająca autoryzację JWT na wszystkich endpointach poza `/auth/register` i `/auth/login`.

---

## Architektura projektu (backend i frontend)

**Backend (Spring Boot):**
- Wzorzec MVC:
    - Model (encje JPA, DTO)
    - Controller (REST API)
    - Service (logika, walidacja, bezpieczeństwo)
- Każda operacja na danych przez endpoint REST API
- Bezpieczeństwo: JWT, separacja użytkowników, ochrona endpointów
- Testy jednostkowe i integracyjne (MockMvc, testy repozytoriów)
- Dokumentacja Swagger/OpenAPI

**Frontend (React SPA):**
- SPA – pojedyncza aplikacja z routingiem (React Router)
- Komunikacja przez axios/fetch (z nagłówkiem JWT)
- Formularze: rejestracja, logowanie, dodawanie/edycja psów i wpisów dziennych
- Dashboard: kafelki z podsumowaniem opieki, ranking punktów, widget pogodowy
- Minimalistyczny, pastelowy interfejs, responsywność
- Pełna obsługa błędów i walidacji

---
## Raport zgodności z wymaganiami MopStat-specyfikacja.docx

### 4.1. Wymagania funkcjonalne

| Wymaganie                                                                              | Realizacja w MopStat                                                | Typ      |
|----------------------------------------------------------------------------------------|---------------------------------------------------------------------|----------|
| Możliwość tworzenia i logowania się na profile użytkowników                            | Zrealizowane: rejestracja i logowanie, JWT, formularze UI           | MUST     |
| Każdy użytkownik widzi tylko swoje dane i swoich pupili                                | Zrealizowane: separacja danych po userId, autoryzacja JWT           | MUST     |
| Możliwość dodawania psów do listy opieki                                               | Zrealizowane: pełny CRUD psów (dodawanie, edycja, usuwanie)         | MUST     |
| Rejestrowanie codziennych zdarzeń: karmienie, spacer, kupa, nastrój                    | Zrealizowane: pełny CRUD wpisów dziennych, walidacja, UX-friendly   | MUST     |
| Wgląd w historię zdarzeń dla każdego psa                                               | Zrealizowane: widok historii wpisów, filtrowanie po psie            | SHOULD   |
| Prosty system punktacji za opiekę                                                      | Zrealizowane: punkty za wpis dzienny, dashboard z rankingiem        | COULD    |
| Eksport danych do pliku CSV                                                            | Zrealizowane: endpoint i przycisk eksportu CSV                      | WON'T\*  |
| Przypomnienia o obowiązkach (opcjonalnie)                                              | Niezaimplementowane w MVP                                           | WON'T    |

\* Eksport CSV został zaimplementowany mimo że w specyfikacji miał status "won't" (dodatkowa wartość MVP).

---

### 4.2. Wykorzystywane narzędzia i technologie

| Narzędzie/Technologia         | Specyfikacja MopStat        | Realizacja w projekcie              |
|-------------------------------|-----------------------------|-------------------------------------|
| JavaFX                        | Desktop GUI                 | Spring Boot REST API (web)          |
| SQLite                        | Baza lokalna                | H2/SQLite/PostgreSQL (JPA/Hibernate)|
| JUnit                         | Testy jednostkowe           | JUnit + MockMvc (testy backendu)    |
| PlantUML                      | Diagramy klas               | Możliwość dodania na życzenie       |
| Apache Commons CSV             | Eksport CSV                 | Eksport CSV przez własny endpoint   |
| IntelliJ IDEA                 | IDE                         | IntelliJ, VS Code                   |
| React SPA                     | Brak (desktop w spec.)      | React SPA (nowoczesny frontend)     |

---

### 4.3. Założenia testowe

| Założenie testowe                                                   | Realizacja/testy w MopStat                              |
|---------------------------------------------------------------------|---------------------------------------------------------|
| Rejestracja i logowanie użytkowników                                | Testy kontrolera, testy formularzy UI                   |
| Ograniczenie widoczności danych do własnych użytkowników            | Testy 401/403, próby dostępu do cudzych danych          |
| Dodawanie psów i poprawne ich przypisywanie do użytkownika          | Testy CRUD psów, separacja po userId                    |
| Rejestrowanie codziennych zdarzeń i ich poprawne przypisanie        | Testy CRUD wpisów dziennych                             |
| Prawidłowe zapisywanie i odczytywanie danych z bazy                 | Testy integracyjne, testy repozytoriów                  |
| Generowanie raportu CSV zgodnego z zapisanymi danymi                | Testy eksportu CSV, porównanie danych                   |
| Testy odporności na błędy (brak danych, błędny zapis)               | Testy błędów backendu, walidacja, obsługa w UI          |
| Wszystkie akcje kończą się sukcesem bez błędów krytycznych          | Brak crashy w testach i na froncie                      |
| Dane dostępne tylko dla zalogowanego użytkownika                    | JWT, testy autoryzacji                                  |
| Dane nie giną między restartami aplikacji                           | Testowane na H2/SQLite/PostgreSQL                       |
| Testy jednostkowe przechodzą pomyślnie                              | Pokrycie testami: kontrolery, serwisy, repozytoria      |

---

### 4.4. Terminy

| Zadanie                                 | Data/specyfikacja   | Status        |
|------------------------------------------|---------------------|---------------|
| Wstępna specyfikacja techniczna          | 27.04.2025          | Zrealizowano  |
| Termin końcowy (deadline)                | 07.06.2025          | Zrealizowano  |

---
## Integracja zewnętrznego API pogodowego

W ramach projektu MopStat zaimplementowano integrację z zewnętrznym API pogodowym (Open-Meteo).  
Aplikacja pobiera aktualną pogodę dla lokalizacji (Giżycko) i prezentuje ją na dashboardzie użytkownika.

- **Backend:** endpoint `/api/weather` pobiera dane z Open-Meteo, mapuje je na czytelny opis oraz emoji.
- **Frontend:** widget pogodowy na dashboardzie, wywołanie endpointu podczas ładowania strony.
- **Bezpieczeństwo:** endpoint pogodowy dostępny tylko dla zalogowanych użytkowników (wymagany JWT).

---

## Zrzuty ekranu

- Strona logowania: minimalistyczny formularz z walidacją błędów.
- Formularz rejestracji: prosty układ, przekierowanie po sukcesie.
- Dashboard: lista psów, ranking punktów, kafelki z podsumowaniem opieki, widget pogodowy.
- Widok szczegółów psa: historia wpisów dziennych, możliwość dodania/edycji wpisu.
- Dodawanie psa lub wpisu dziennego: czytelne, pastelowe formularze z walidacją.
- Eksport danych: przycisk generujący plik CSV z danymi użytkownika.

---

## Bezpieczeństwo i JWT

- Każdy użytkownik po rejestracji/logowaniu otrzymuje indywidualny token JWT.
- Każde żądanie do chronionych endpointów (`/api/dogs`, `/api/score/summary`, `/api/export/csv`, `/api/weather`) wymaga nagłówka `Authorization: Bearer <token>`.
- Backend sprawdza ważność tokenu i uprawnienia — użytkownik widzi/edycja tylko swoje psy, wpisy, punkty.
- Próba użycia nieprawidłowego tokenu skutkuje błędem 401 Unauthorized; próba edycji cudzych danych — 403 Forbidden.
- Wszystkie odpowiedzi backendu przekazywane są przez DTO (Data Transfer Object) — brak ryzyka ujawnienia wrażliwych danych (np. hasła, e-maila).

---

## Możliwości rozbudowy (propozycje)

- Kolejne integracje API: kursy walut NBP, Dog Facts, losowy GIF dnia, wyszukiwarka najbliższych schronisk.
- Panel admina: statystyki, zarządzanie użytkownikami, rejestr błędów.
- Filtrowanie i sortowanie historii według psa, daty, rodzaju aktywności.
- Wersja mobilna: pełna responsywność, dedykowany tryb dark/light.
- Powiadomienia: e-mail/Push o nowych wpisach lub przypomnienia o spacerze/karmieniu.
- Integracja z social media: możliwość udostępniania osiągnięć psa/opiekuna.
- Dalsza grywalizacja: rankingi tygodniowe, status "opiekun miesiąca", odznaki.

---

## Wnioski końcowe

Projekt MopStat spełnia wszystkie wymagania z dokumentu „MopStat-specyfikacja.docx” oraz założenia przedmiotu Inżynieria Oprogramowania.  
Aplikacja jest kompletna, stabilna i gotowa do prezentacji lub dalszego rozwoju.

Podczas realizacji projektu rozwinąłem umiejętności w zakresie:

- projektowania i implementacji pełnej aplikacji webowej (Spring Boot + React SPA)
- integracji z zewnętrznymi API
- bezpieczeństwa JWT i architektury DTO
- testowania (MockMvc, testy jednostkowe/integracyjne)
- tworzenia dokumentacji technicznej

Kod MopStat jest czytelny, testowalny i łatwy do dalszej rozbudowy.

---

*Opracował: Adam Ledziński*  
*Data: czerwiec 2025*

---
