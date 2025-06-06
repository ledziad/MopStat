
# **MopStat – Raport podsumowujący sprint “CRUD + friendly UX + pastelowy MopStat”**

---

## **1. Streszczenie ostatniego sprintu i stanu projektu**

### **Sprint “CRUD + friendly UX + pastelowy MopStat” — ZAMKNIĘTY!**

**Stan końcowy na dzień dzisiejszy:**

* **Pełny CRUD dla psów i wpisów dziennych działa** na froncie i backendzie.
* Spójne, **pastelowe UI** z przyjaznymi alertami, potwierdzeniami usuwania i pełnym workflow użytkownika.
* **Przyciski “Usuń”** i formatowanie w jednym wierszu.
* **Zero “sierot”** – kaskadowe usuwanie, dane odświeżają się poprawnie po każdej akcji.
* **Kod uporządkowany**, przejrzysty, gotowy do wdrożenia, zgodnie z checklistą MVP.

---

## **2. Podstawowe założenia (z “MopStat-specyfikacja.docx”)**

**Cel projektu:**

* Lekka aplikacja do codziennego rejestrowania opieki nad psami — karmienie, spacery, kupa, nastrój.
* Motywacja poprzez system punktacji, humorystyczny styl i przyjazny UI.

**Wymagania funkcjonalne (must/should/could/won’t):**

* **Rejestracja/logowanie użytkownika** (JWT) — \[MUST]
* Każdy widzi **tylko swoje psy i wpisy** — \[MUST]
* **Dodawanie psów** do listy opieki — \[MUST]
* **Rejestrowanie dziennych zdarzeń** (karmienie, spacery, kupa, nastrój) — \[MUST]
* **Historia wpisów** — \[SHOULD]
* **System punktacji** — \[COULD, zaimplementowany]
* **Eksport danych CSV** — \[WON’T, potem]
* **Przypomnienia o obowiązkach** — \[opcjonalnie/WON’T na teraz]

**Technologie (planowane w specyfikacji):**

* Backend: JavaFX, SQLite, JUnit, PlantUML, Apache Commons CSV, IntelliJ
* (Wersja webowa, zgodnie z wymaganiami kursu: Spring Boot + React SPA)

---

## **3. Realizacja vs założenia projektu (wypunktowane porównanie)**

### **A. WYMAGANIA FUNKCJONALNE – STAN NA DZISIAJ**

| Funkcja                              |         Status (realizacja)         | Uwagi                                                   |
| ------------------------------------ | :---------------------------------: | ------------------------------------------------------- |
| **Rejestracja/logowanie (JWT)**      |         **✔️ Zrealizowane**         | Backend + frontend, pełny workflow, ochrona endpointów  |
| **Widoczność tylko własnych danych** |         **✔️ Zrealizowane**         | JWT, userId, pełna separacja, testy błędów 401/403      |
| **Dodawanie/usuwanie psów**          |      **✔️ Zrealizowane (CRUD)**     | Formularze, walidacja, pastelowe UI, kaskadowe usuwanie |
| **Rejestrowanie dziennych zdarzeń**  |      **✔️ Zrealizowane (CRUD)**     | Dodawanie/edycja/usuwanie wpisów, UX-friendly           |
| **Historia wpisów**                  |         **✔️ Zrealizowane**         | Lista wpisów na stronie psa, filtrowanie po psie        |
| **System punktacji**                 |         **✔️ Zrealizowane**         | Punkty za każdy wpis, dashboard z kaflami i sumami      |
| **Eksport CSV**                      |         **✔️ Zrealizowane**         | Przycisk “Pobierz CSV”, integracja backend+frontend     |
| **Przypomnienia/Notyfikacje**        | 🚫 (opcjonalnie, nieimplementowane) | Zgodnie z MVP, “won’t have”                             |

---

### **B. TECHNOLOGIE I ARCHITEKTURA – STAN NA DZISIAJ**

| Założenie/specyfikacja                          | Stan realizacji | Komentarz                                               |
| ----------------------------------------------- | :-------------: | ------------------------------------------------------- |
| **Spring Boot + React SPA**                     |      **✔️**     | Zrealizowano zgodnie z wymogiem webowym                 |
| **Model danych DTO-only, pełny podział warstw** |      **✔️**     | Brak zwracania encji, DTO, MockMvc/testy integracyjne   |
| **JWT Security, ochrona danych**                |      **✔️**     | Każdy user widzi tylko swoje dane, 401/403              |
| **Swagger/OpenAPI**                             |      **✔️**     | Automatyczna dokumentacja/testowanie API                |
| **Testy jednostkowe/integracyjne**              |      **✔️**     | Pokrycie kontrolerów, serwisów, repozytoriów            |
| **Eksport CSV (REST)**                          |      **✔️**     | Pobranie CSV dla zalogowanego usera                     |
| **Kaskadowe usuwanie danych**                   |      **✔️**     | Usunięcie psa = usunięcie powiązanych wpisów i punktów  |
| **Pastelowy, minimalistyczny frontend**         |      **✔️**     | Kolory, spacing, UX w stylu pastelowym, “FriendlyAlert” |

---

## **4. Wnioski i ocena – stan na dzień dzisiejszy**

* Projekt **w pełni spełnia wymagania z oryginalnej specyfikacji** – a nawet je wyprzedza (np. obsługa CSV, dashboard punktów, ochrona JWT, UI pastelowe).
* Każda funkcjonalność MVP **działa, jest przetestowana** i prezentuje dobry standard kodu i architektury.
* **Brak “sierot”, brak błędów krytycznych, synchronizacja front-backend, wygodne UX.**
* Kod jest **gotowy do rozbudowy o nowe ficzery** lub testy demo online.

---
