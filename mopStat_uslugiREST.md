# Spis treści

1. [Opis projektu](#opis-projektu)
2. [Najważniejsze pojęcia — MopStat Backend](#najważniejsze-pojęcia--mopstat-backend)
3. [Architektura projektu (backend i frontend)](#architektura-projektu-backend-i-frontend)
4. [Raport zgodności z wymaganiami](#raport-zgodności-z-wymaganiami)
5. [Integracja zewnętrznego API pogodowego](#integracja-zewnętrznego-api-pogodowego)
6. [Zrzuty ekranu](#zrzuty-ekranu)
7. [Bezpieczeństwo i JWT](#bezpieczeństwo-i-jwt)
8. [Możliwości rozbudowy (propozycje)](#możliwości-rozbudowy-propozycje)
9. [Wnioski końcowe](#wnioski-końcowe)

---

## Opis projektu

**MopStat** to webowa aplikacja do codziennego rejestrowania opieki nad psami — karmienia, spacerów, nastrojów, aktywności.  
Projekt powstał, by wesprzeć właścicieli i fundacje w lepszej organizacji dnia, z elementami grywalizacji oraz minimalistycznym, pastelowym interfejsem.

**Główne cele:**
- Rejestracja i historia opieki nad psami
- Motywacja przez system punktów i dashboard
- Bezpieczeństwo (JWT)
- Przykład integracji z zewnętrznym API (pogoda)

---

## Najważniejsze pojęcia — MopStat Backend

### **REST API**
REST (Representational State Transfer) — styl projektowania komunikacji przez HTTP.  
REST API = zbiór endpointów (np. `/api/dogs`), przez które aplikacja wymienia dane.

**Typowe metody:**
- GET — pobieranie danych
- POST — tworzenie nowych danych
- PUT — aktualizacja istniejących danych
- DELETE — usuwanie danych

### **JWT (JSON Web Token)**
Cyfrowy token (otrzymywany po zalogowaniu), przechowywany po stronie klienta.  
Przesyłany w nagłówku `Authorization`, chroni API i umożliwia autoryzację użytkowników.

### **CORS (Cross-Origin Resource Sharing)**
Mechanizm bezpieczeństwa przeglądarki. Pozwala łączyć frontend i backend na różnych portach/domenach.  
W Spring Boot: konfiguracja CORS pozwala na połączenia z React (`localhost:5173`).

### **DTO (Data Transfer Object)**
Klasa służąca tylko do przesyłania danych między backendem i frontendem.  
Dzięki temu aplikacja nie ujawnia całych encji bazy.

### **Swagger / OpenAPI**
Narzędzie do automatycznej dokumentacji REST API.  
Umożliwia interaktywne testowanie endpointów przez UI, ważne przy prezentacji i debugowaniu.

### **Encja JPA / Repozytorium JPA**
Encja = klasa reprezentująca tabelę w bazie (np. Dog, User), opatrzona adnotacją `@Entity`.  
Repozytorium JPA — interfejs pozwalający na CRUD bez pisania SQL.

### **Serwis (service/)**
Tu znajduje się logika biznesowa (np. walidacja, wyliczenia punktów, bezpieczeństwo).

### **SecurityConfig / Filtr JWT**
Konfiguracja bezpieczeństwa Spring Security, wymuszająca JWT na wszystkich endpointach poza rejestracją i logowaniem.  
Filtr JWT weryfikuje każdy request.

---
## Architektura projektu (backend i frontend)

Projekt MopStat dzieli się na dwie warstwy:

### **Backend (Spring Boot)**
- Wzorzec MVC: Model (baza, encje JPA), View (JSON/DTO), Controller (REST API)
- Każda operacja na danych odbywa się przez dedykowany endpoint (`/api/dogs`, `/api/score/summary`, `/api/weather` itd.)
- Bezpieczeństwo realizowane przez JWT i konfigurację Spring Security — tylko zalogowany użytkownik widzi/edytuje swoje psy i wpisy
- Swagger generuje interaktywną dokumentację API i umożliwia testy bezpośrednio z poziomu przeglądarki
- Testy jednostkowe/integracyjne (MockMvc, testy repozytoriów, testowanie endpointów)

### **Frontend (React SPA)**
- Aplikacja jednostronicowa (Single Page Application)
- Routing między stronami (React Router): logowanie, rejestracja, dashboard, szczegóły psa, edycja, historia
- Wszystkie żądania HTTP do backendu są wykonywane przez axios/fetch z nagłówkiem JWT
- Dane wyświetlane w przejrzystych kafelkach i wykresach na dashboardzie
- Widget pogodowy oraz ranking punktów dla psów na głównej stronie
- Pełna obsługa walidacji błędów i komunikatów

---

## Raport zgodności z wymaganiami

### **1. Komunikacja HTTP**
- **GET, POST, PUT, DELETE** — każda metoda HTTP jest obsługiwana przez kontrolery REST API
- CRUD (tworzenie, pobieranie, edycja, usuwanie) dostępny dla psów i wpisów dziennych

### **2. Obsługa błędów**
- Zwracane kody: **400** (złe żądanie), **401** (brak autoryzacji), **403** (brak dostępu), **404** (nie znaleziono)
- Przejrzyste komunikaty w odpowiedzi z backendu, przechwytywane na froncie

### **3. Integracje z zewnętrznymi API**
- **Spełniono** — zaimplementowano **integrację pogodową (Open-Meteo)**:
  - Endpoint `/api/weather` pobiera pogodę dla Giżycka i zwraca opis, temperaturę oraz emoji
  - Widget na dashboardzie pokazuje pogodę każdemu zalogowanemu użytkownikowi
- Druga integracja (np. kursy walut, żarty, GIFy) możliwa do wdrożenia w kolejnych wersjach

### **4. JWT — autoryzacja i bezpieczeństwo**
- Pełna obsługa JWT: generowanie tokenu przy logowaniu/rejestracji
- Wszystkie endpointy (poza `/auth/register`, `/auth/login`) wymagają ważnego tokenu w nagłówku `Authorization`
- Rozdzielenie danych — użytkownik nie ma dostępu do danych innych userów

### **5. Testy jednostkowe**
- Testy jednostkowe i integracyjne obejmują serwisy, repozytoria oraz kontrolery (np. MockMvc, testy odpowiedzi na błędne żądania)
- Sprawdzane są przypadki braku danych, błędnej autoryzacji, poprawności zapisów

### **6. Swagger/OpenAPI**
- W pełni skonfigurowana dokumentacja Swagger UI dla wszystkich endpointów
- Możliwość ręcznego testowania każdego zapytania bezpośrednio w przeglądarce

### **7. Prosty frontend**
- SPA (React) — przejrzysty, minimalistyczny interfejs
- Formularze do logowania/rejestracji, dodawania psów, wpisów, eksportu danych (CSV)
- Widget pogodowy oraz wykres punktów — podnosi użyteczność dashboardu

---

## Integracja zewnętrznego API pogodowego

W ostatnim sprincie wdrożono pierwszą zewnętrzną integrację — API pogodowe Open-Meteo.

**Jak działa integracja?**
- Backend (`/api/weather`) łączy się z serwisem Open-Meteo, pobierając dane pogodowe (aktualna temperatura, kod pogodowy)
- Odpowiedź mapowana jest na czytelny opis pogody i emoji (np. ☀️, 🌧️, ❄️)
- Frontend (React) wywołuje `/api/weather` przy starcie dashboardu — widget pogodowy od razu wyświetla aktualne dane
- Endpoint wymaga autoryzacji JWT (spójność z całą aplikacją)

**Efekt dla użytkownika:**
- Pogoda w Giżycku zawsze dostępna na głównej stronie
- Widget atrakcyjny wizualnie, zwiększa przydatność i „fun” aplikacji

**Znaczenie integracji:**
- Pokazuje, że aplikacja korzysta z danych zewnętrznych i nie jest tylko „lokalnym CRUDem”
- Stanowi wzorzec do kolejnych integracji (kursy walut, memy, dowcipy, itp.)
- Ułatwia prezentację na zaliczeniu (pełen cykl: pobranie z zewnątrz → mapowanie na backendzie → prezentacja na froncie)

---
## Zrzuty ekranu

- **Strona logowania:** minimalistyczny formularz z walidacją błędów.
- **Rejestracja:** prosty formularz z przekierowaniem po sukcesie.
- **Dashboard po zalogowaniu:** lista psów, ranking punktów, kafelki z podsumowaniem opieki, **widget pogodowy** na górze strony.
- **Widok szczegółów psa:** lista wpisów dziennych, możliwość dodania/edycji wpisu.
- **Dodawanie nowego psa/wpisu:** czytelne, pastelowe formularze z walidacją.
- **Eksport do CSV:** przycisk generujący plik eksportowy z danymi użytkownika.

*(Wklej własne screeny lub wrzuć tu obrazy — najlepiej PNG, szerokość do 800 px, podpis pod obrazkiem np. "Rys. 1. Dashboard aplikacji MopStat")*

---

## Bezpieczeństwo i JWT

- **JWT** — każdy użytkownik po zalogowaniu/rejestracji otrzymuje token, przechowywany w localStorage.
- Każde żądanie do chronionych endpointów (np. `/api/dogs`, `/api/score/summary`, `/api/weather`) wymaga nagłówka `Authorization: Bearer <token>`.
- Backend sprawdza ważność tokenu i uprawnienia — user widzi/edycja tylko swoje psy, wpisy, punkty.
- Próba użycia nieprawidłowego tokenu kończy się błędem **401 Unauthorized**; próba edycji cudzych danych — **403 Forbidden**.
- Dodatkowo: każda odpowiedź idzie przez DTO (Data Transfer Object), więc nie ma ryzyka wycieku wrażliwych danych (np. hash hasła, adres e-mail).

---

## Możliwości rozbudowy (propozycje)

- **Kolejne integracje API:** np. kursy walut NBP, Dog Facts, losowy GIF dnia (Giphy), wyszukiwarka najbliższych schronisk.
- **Panel admina:** statystyki, zarządzanie użytkownikami, rejestr błędów.
- **Filtrowanie i sortowanie historii:** wg psa, daty, rodzaju aktywności.
- **Wersja mobilna:** pełna responsywność + dedykowany tryb dark/light.
- **Powiadomienia:** e-mail/Push o nowych wpisach lub przypomnienia o spacerze/karmieniu.
- **Integracja z social media:** możliwość udostępniania osiągnięć psa/opiekuna.
- **Dalsza grywalizacja:** rankingi tygodniowe, status "opiekun miesiąca", odznaki.

---

## Wnioski końcowe

Projekt MopStat to przykład nowoczesnej, lekkiej aplikacji webowej łączącej:
- klasyczną architekturę backendu (Spring Boot, REST API, DTO, testy, JWT, Swagger)
- prosty i czytelny frontend (React SPA)
- oraz integrację z danymi zewnętrznymi (API pogodowe Open-Meteo)

Aplikacja spełnia wszystkie wymagania z dokumentacji projektowej i jest gotowa do prezentacji lub rozwoju.  
Podczas pracy rozwinąłem umiejętności w zakresie:
- integracji API, bezpieczeństwa (JWT), dokumentacji (Swagger)
- projektowania przejrzystego interfejsu użytkownika
- testowania i walidacji danych

**MopStat jest użyteczny na co dzień, a jego kod i architektura są czytelne i łatwe do dalszej rozbudowy.**

---

