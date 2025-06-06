
# **Raport zgodności projektu MopStat (branch: mopsik) z wymaganiami MVC**

---

## **1. Wymagania ogólne (“MVC wymagania.pdf”) a realizacja w projekcie MopStat**

### **Ocena 3.0**

* Wyraźny podział na warstwy (modele, kontrolery, widoki).
* Projekt w formie aplikacji internetowej: backend (Spring Boot) + frontend (React SPA).
* Komunikacja backend-frontend przez REST API.
* Brak błędów krytycznych i poprawne działanie całości.

### **Realizacja w MopStat:**

* Podział na warstwy:

  * Backend: `/controller`, `/service`, `/repository`, `/dto`.
  * Frontend: React SPA, podział na strony i komponenty.
* Backend: Spring Boot 3.x REST API.
* Frontend: React, axios, react-router-dom.
* Komunikacja przez REST API.
* Działanie przetestowane, brak crashy.

---

### **Ocena 4.0**

* Wykorzystanie bazy danych.
* Prosty system autentykacji/logowania.

### **Realizacja w MopStat:**

* Użycie JPA/Hibernate (baza: H2/SQLite/PostgreSQL).
* System autentykacji/logowania oparty o JWT.
* Oddzielne repozytoria JPA.

---

### **Ocena 4.5**

* Odświeżenie strony nie powoduje wylogowania użytkownika.

### **Realizacja w MopStat:**

* JWT przechowywany w localStorage.
* Po odświeżeniu użytkownik pozostaje zalogowany.

---

### **Ocena 5.0**

* Podstrony umożliwiające dodawanie i usuwanie elementów (np. postów/wpisów).
* Edycja treści dostępna tylko dla zalogowanych użytkowników.

### **Realizacja w MopStat:**

* Dodawanie, edycja i usuwanie psów i wpisów dziennych na dedykowanych podstronach.
* Komponenty React: `AddDogPage.jsx`, `EditDogPage.jsx`, `AddRecordPage.jsx`, `EditDailyRecordPage.jsx`.
* Edycja i usuwanie dostępne wyłącznie po zalogowaniu (autoryzacja JWT).
* Backend zwraca odpowiednie kody błędów (401/403).

---

## **2. Struktura projektu MopStat (branch: mopsik)**

### **Backend (Spring Boot)**

* `/controller/` — obsługa endpointów API.
* `/service/` — logika biznesowa, walidacja.
* `/repository/` — operacje CRUD na encjach.
* `/dto/` — klasy DTO, brak ekspozycji encji.
* `/config/` — konfiguracja JWT, CORS, bezpieczeństwa.
* `/exception/` — obsługa wyjątków.
* `/test/` — testy kontrolerów, serwisów, repozytoriów.

### **Frontend (React SPA)**

* Komponenty podzielone na strony (`/src/pages`).
* Routing i ochrona tras (react-router-dom, JWT).
* Formularze dodawania, edycji i usuwania danych.
* Pastelowy, minimalistyczny UI.
* Alerty i potwierdzenia akcji.
* Eksport CSV, dashboard, sumowanie wpisów.

---

## **3. Podsumowanie zgodności**

Projekt MopStat (branch: mopsik) spełnia wszystkie wymagania opisane w dokumencie “MVC wymagania.pdf” w zakresie:

* Wyraźnego podziału na warstwy MVC,
* Komunikacji przez REST API,
* Użycia bazy danych,
* Systemu autentykacji/logowania,
* Zachowania sesji po odświeżeniu,
* Dodawania, edycji i usuwania danych przez podstrony tylko dla zalogowanych użytkowników,
* Braku błędów krytycznych,
* Poprawnej struktury oraz testów.

---

Raport opracowano na podstawie wymagań zawartych w “MVC wymagania.pdf” oraz analizy aktualnej struktury i funkcjonalności projektu MopStat (branch: mopsik, repozytorium: [https://github.com/ledziad/MopStat/tree/mopsik](https://github.com/ledziad/MopStat/tree/mopsik)).

---
