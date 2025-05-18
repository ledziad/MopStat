


## 🚀 **Jak uruchomić backend MopStat (Spring Boot)**

1. **Wymagania wstępne:**

    * Java 17+ (zalecane JDK 21 lub wyżej)
    * Maven 3.6+
    * (Opcjonalnie: IntelliJ IDEA lub VSCode z wtyczką Java)
    * GIT (jeśli klonujesz repo)

2. **Klonowanie projektu:**

   ```bash
   git clone https://github.com/ledziad/MopStat.git
   cd MopStat
   ```

3. **Build projektu (opcjonalnie, Maven):**

   ```bash
   mvn clean install
   ```

4. **Uruchomienie aplikacji:**

   ```bash
   mvn spring-boot:run
   ```

   Lub w IntelliJ:

    * Otwórz projekt → kliknij prawym na klasę z adnotacją `@SpringBootApplication` (np. `MopStatApplication`) → **Run 'MopStatApplication'**.

5. **Dostęp do API:**

    * Backend domyślnie działa pod adresem:
      `http://localhost:8080`
    * Dokumentacja interaktywna (Swagger UI):
      `http://localhost:8080/swagger-ui.html`
      lub
      `http://localhost:8080/swagger-ui/index.html`

6. **Baza danych:**

    * Domyślnie H2 (w pamięci, do testów/dev).
    * Konsola H2:
      `http://localhost:8080/h2-console`
      (login: `sa`, hasło puste, JDBC URL znajdziesz w `application.properties`)

7. **Domyślna konfiguracja JWT:**

    * Endpoint rejestracji/logowania: `/api/auth/register`, `/api/auth/login`
    * Po zalogowaniu – token JWT należy przekazywać w nagłówku:
      `Authorization: Bearer <token>`

---