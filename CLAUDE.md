# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Corneteiro** is a Spring Boot REST API that consumes the Cartola API (Brazilian fantasy football) to provide endpoints for searching and listing players (atletas) and clubs (clubes). The project is being professionalized across 7 phases to reach enterprise-ready standards.

**Key Context:** Cartola is a fantasy football game focused on Brazilian Série A. Examples and test data should use players from the Brasileirão or Brazil, not international leagues.

**Phase Status:** Currently on **Phase 2** (DTOs converted to Records). Phases 3-7 (Cache, Swagger, Tests, Docker, CI/CD) are planned.

---

## Tech Stack

- **Java 21** with Spring Boot 4.0.4
- **Maven** for build management
- **Java Records** for immutable DTOs (no `rodadaId` — it's API context, not player data)
- **Caffeine** for in-memory caching (config: max 10 items, 5-minute expiry)
- **Swagger/OpenAPI** for API documentation
- **Mockito** for unit testing
- **Docker** & docker-compose for containerization
- **GitHub Actions** for CI/CD pipeline

---

## Architecture

**Data Flow:** `CartolaApiClient` (REST calls) → `MercadoProvider` (validation; cache lives in `CartolaApiClient`) → `Services` (business logic) → `Controllers` (HTTP endpoints)

**Key Patterns:**
- **@Bean**: Creates reusable singletons (e.g., `RestClient`)
- **@Cacheable**: Decorates methods to cache results (applied to the public `CartolaApiClient.buscarMercado()`; it does NOT work on private methods or self-invocation, since Spring applies it via proxy)
- **@Component/@Service**: Spring auto-injection
- **Records**: Immutable DTOs with built-in accessors (e.g., `atleta.nome()` not `atleta.getNome()`)
- **GlobalExceptionHandler**: Centralized error handling for API responses

**Folder Structure:**
- `client/` — REST client for Cartola API
- `config/` — Spring configuration (RestClientConfig with @Bean)
- `controller/` — HTTP endpoints (AtletaController, ClubeController)
- `service/` — Business logic (AtletaService, ClubeService, MercadoProvider)
- `dto/` — Data Transfer Objects as Records (Atleta*, Clube*, MercadoResponse)
- `exception/` — Custom exceptions with GlobalExceptionHandler

---

## Common Commands

**Build & Compile**
```bash
mvn clean compile        # Clean build, compile only
mvn clean package        # Build JAR without tests
```

**Testing**
```bash
mvn test                 # Run all tests
mvn test -Dtest=AtletaServiceTest  # Run single test class
```

**Run Locally**
```bash
mvn spring-boot:run      # Start dev server on http://localhost:8080
curl http://localhost:8080/atletas  # Test endpoints
```

**Docker**
```bash
docker-compose up        # Start containerized app
docker-compose down      # Stop containers
```

**Check Compilation**
```bash
mvn clean compile        # Verify no errors (used at end of each phase)
```

---

## Implementation Phases

| Phase | Focus | Status |
|-------|-------|--------|
| 1 | pom.xml + application.yml + RestClientConfig | ✅ Complete |
| 2 | Convert DTOs to Records (remove getters) | ✅ Complete |
| 3 | Add @EnableCaching + @Cacheable to MercadoProvider | 🔄 Next |
| 4 | Swagger annotations (@Operation, @Tag) | 📝 Planned |
| 5 | Unit tests (MercadoProviderTest, AtletaServiceTest, ClubeServiceTest) | 📝 Planned |
| 6 | Docker + docker-compose setup | 📝 Planned |
| 7 | GitHub Actions CI/CD pipeline | 📝 Planned |

Reference: `GUIA_PROFISSIONALIZACAO.md` contains detailed step-by-step instructions for each phase.

---

## Key Design Decisions

**No `rodadaId` in AtletaDTO:** The round ID is API context (which round data is from), not player data. Each player has static attributes (name, position, club) that don't change per round. Removed to keep the model clean.

**RestClientConfig with @Bean:** Single point for REST client configuration. The URL is injected from `application.yml` (`cartola.api.base-url`), making it environment-aware.

**Caffeine over other caches:** Lightweight, in-memory cache suitable for Fantasy Football API data that refreshes periodically (not constantly changing).

---

## Important Notes for Claude

1. **Records use different syntax:** `record.campo()` instead of `record.getCampo()`. Check for this when working with DTOs.
2. **@Value("${cartola.api.base-url}")** injects from `application.yml`. Don't hardcode URLs.
3. **MercadoProvider validates, CartolaApiClient caches:** All API calls go through MercadoProvider; the `mercado` cache is on `CartolaApiClient.buscarMercado()`. Guarded by `MercadoProviderCacheTest`.
4. **Test setup creates Records:** When mocking AtletaDTO, use constructor syntax: `new AtletaDTO(1, "Nome", "apelido", ...)`
5. **Keep examples contextualized:** Use Arrascaeta, Neymar, Rodrygo (Brasileirão players), not international league players.
