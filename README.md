# Fullstack birthday app

A Spring Boot 4 API with a React frontend for tracking people's nicknames and birthdays.

| Module | What it is |
|--------|------------|
| `rest` | REST API, JPA entity, Liquibase schema (H2 in-memory database) |
| `web`  | React app (Vite). Maven builds it and packages it as static files |
| `app`  | Runnable jar that bundles `rest` and `web` |

## Build and run

Requires Java 21. Maven downloads its own copy of Node for the frontend build.

```bash
mvn verify                          # builds everything and runs backend + frontend tests
java -jar app/target/fullStackApp.jar
```

Then open http://localhost:8080.

## Develop

Run the API and the frontend dev server side by side:

```bash
mvn -pl rest spring-boot:run -Dspring-boot.run.profiles=dev   # API on :8080, H2 console at /h2-console
cd web && npm install && npm run dev                          # UI on :3000, proxies /api to :8080
```

Frontend tests: `cd web && npm test`.

## API

| Method | Path               | Result |
|--------|--------------------|--------|
| GET    | `/api/people`      | everyone, with their current age |
| GET    | `/api/people/{id}` | one person, or 404 |
| POST   | `/api/people`      | 201 with the new person; 400 if a field is missing or the birthday is in the future |

Request body for POST:

```json
{ "firstName": "Daniel", "lastName": "Talik", "nickname": "Dino", "birthday": "1988-09-20" }
```

Errors are returned as RFC 9457 problem details.
The database is in memory, so data resets when the app restarts.
