# Task Manager

A REST API for managing tasks. This is a learning project: I'm using it to
learn Spring Boot and I extend different parts of it as I go. Nothing is
planned ahead, so don't assume upcoming features.

## Stack
- Java 21, Spring Boot 4.1.1, Maven
- PostgreSQL (database `taskdb`, user `taskapp`, `localhost:5432`)
- Spring Data JPA (Hibernate) for persistence
- MapStruct for entity ↔ DTO mapping
- `jackson-databind-nullable` (`JsonNullable`) for PATCH requests

## Setup and commands
- Copy `.env.example` to `.env` and set `DB_PASSWORD`. `application.properties`
  imports `.env`, and `.env` is gitignored.
- Postgres must be running before you start the app.
- Run: `./mvnw spring-boot:run`
- Test: `./mvnw test`. There are no real tests yet, only a `contextLoads()`
  test, and it needs the database running.
- I test endpoints manually in Bruno.

## Structure
Package root: `com.dev.taskmanager`
- `controller` → `service` → `repository` (layered)
- `model`: JPA entities (`Task`)
- `dto`: request and response records
- `mapper`: MapStruct mappers, including `JsonNullableMapper` for PATCH
- `exception`: custom exceptions

## Schema
- `src/main/resources/schema.sql` is the source of truth for the schema. It runs
  on every startup (`spring.sql.init.mode=always`).
- Hibernate only validates the schema (`ddl-auto=validate`). If you change an
  entity, change `schema.sql` to match, or the app won't start.

## Rules
- Controllers call services, never repositories directly.
- Only repositories touch persistence.
- Prefer derived query methods. Any `@Query` uses named parameters (`:name`),
  never string concatenation.
- Controllers take and return DTOs, never the `Task` entity.
- Map between entities and DTOs with MapStruct, not by hand.
- Ask before adding dependencies or changing `pom.xml`.

## Working with me
- Follow the learning/building modes in my global CLAUDE.md. Don't default to
  learning mode in this project.
- If a request is definitely about learning (concepts, explanations,
  exercises), treat it as learning mode without asking. If the mode is unclear,
  ask "Learning or building?" before doing anything else.
- Commit only when I ask.
