
# **Raport zgodności projektu MopStat (branch: mopsik) z wymaganiami “Projekt MopStat REST API.docx”**

---

## **1. Wymagania dotyczące API i backendu**

### **Komunikacja za pomocą protokołu HTTP**

* Wykorzystanie wszystkich metod HTTP:

  * **GET** — pobieranie danych
  * **POST** — tworzenie danych
  * **PUT** — aktualizacja danych
  * **DELETE** — usuwanie danych

**Realizacja w MopStat:**

* Wszystkie metody HTTP zaimplementowane w kontrolerach REST API.
* CRUD dostępny dla psów oraz wpisów dziennych przez dedykowane endpointy.

---

### **Obsługa błędów HTTP**

* Obsługa kodów błędów:

  * **400 Bad Request**
  * **401 Unauthorized**
  * **403 Forbidden**
  * Inne istotne kody błędów

**Realizacja w MopStat:**

* Zwracanie odpowiednich kodów błędów w przypadku nieprawidłowych żądań, braku autoryzacji, dostępu do cudzych danych itp.
* Obsługa wyjątków na backendzie, przejrzyste komunikaty na froncie.

---

### **Integracje z zewnętrznymi API** *(informacja techniczna, obecnie nieimplementowane w MopStat MVP)*

* Wymóg: co najmniej dwie integracje z zewnętrznymi API (np. NBP, PayU).

**Realizacja w MopStat:**

* Funkcjonalność *niezaimplementowana* w aktualnym MVP (zgodnie z wytycznymi MVP – do rozbudowy w przyszłości).

---

### **JWT Autentykacja i Autoryzacja**

* JWT służy do bezpiecznego uwierzytelniania i autoryzacji użytkowników.

**Realizacja w MopStat:**

* Pełna implementacja JWT.
* Token przesyłany w nagłówkach Authorization.
* Ochrona endpointów, separacja danych użytkowników.

---

### **Testy jednostkowe**

* Wymagane testy jednostkowe dla kluczowych części kodu.

**Realizacja w MopStat:**

* Testy jednostkowe i integracyjne dla kontrolerów, serwisów, repozytoriów (MockMvc, testy integracyjne).

---

### **Open API Documentation (Swagger)**

* Automatyczna dokumentacja REST API przy pomocy Swagger/OpenAPI.

**Realizacja w MopStat:**

* Pełna integracja Swagger UI.
* Dokumentacja i testowanie endpointów przez interfejs graficzny.

---

### **Prosty frontend**

* Interfejs użytkownika umożliwiający pełną obsługę CRUD przez REST API.
* Brak wymogu zaawansowanego designu, nacisk na poprawność działania.

**Realizacja w MopStat:**

* Frontend zrealizowany w React SPA.
* Formularze, podstrony, alerty i walidacje.
* Obsługa wszystkich operacji (CRUD, logowanie, rejestracja, eksport CSV).

---

## **2. Struktura projektu MopStat (branch: mopsik) — odniesienie do wymagań**

* **Backend (Spring Boot):**

  * REST API z pełnym zakresem CRUD, obsługa błędów, testy, dokumentacja Swagger.
  * JWT, ochrona endpointów.
* **Frontend (React SPA):**

  * Wykorzystanie endpointów REST, ochrona tras, pełen workflow użytkownika.

---

## **3. Podsumowanie zgodności**

Projekt MopStat (branch: mopsik) spełnia wszystkie wymagania z dokumentu “Projekt MopStat REST API.docx” w zakresie:

* Kompletnego CRUD przez REST API (GET/POST/PUT/DELETE)
* Obsługi kodów błędów HTTP (400, 401, 403 itd.)
* Uwierzytelniania i autoryzacji JWT
* Dokumentacji OpenAPI (Swagger)
* Testów jednostkowych/integracyjnych
* Funkcjonalnego, prostego frontendu obsługującego całość API

Zagadnienia związane z integracją zewnętrznych API nie są obecnie zaimplementowane w MVP (zgodnie z wytycznymi MVP), możliwe do rozbudowy w kolejnych wersjach.

---

Raport opracowano na podstawie wymagań z “Projekt MopStat REST API.docx” oraz analizy struktury i kodu projektu MopStat (branch: mopsik, repozytorium: [https://github.com/ledziad/MopStat/tree/mopsik](https://github.com/ledziad/MopStat/tree/mopsik)).

---
