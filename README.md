# Spring Boot + Docker Compose: two services talking

![Java 17](https://img.shields.io/badge/Java-17-orange)
![Spring Boot 2.7](https://img.shields.io/badge/Spring%20Boot-2.7-brightgreen)
![Docker Compose](https://img.shields.io/badge/Docker-Compose-2496ED)

A minimal example of two Spring Boot services running in separate containers and calling each other by service name on a Docker Compose network.

```
            :8080                         :8081
 you ──▶ client-service ──HTTP──▶ greeting-service
         GET /greeting            GET /greeting
```

| Service | Port | What it does |
|---|---|---|
| `greeting-service` | 8081 | Returns `Hello from greeting-service` |
| `client-service` | 8080 | Calls `greeting-service` and returns its response; returns `502` if it's unavailable |

## Run it

Requires Docker.

```bash
docker compose up --build
curl http://localhost:8080/greeting
# Hello from greeting-service
```

## How it works

- **Service discovery by name:** inside the Compose network, `client-service` reaches the other container at `http://greeting-service:8081`. The URL is set through the `GREETING_SERVICE_URL` environment variable, so the same code also runs outside Docker (default `http://localhost:8081`).
- **Startup order:** `greeting-service` has a healthcheck, and `client-service` waits for it (`depends_on: condition: service_healthy`).
- **Multi-stage Dockerfiles:** Maven builds the jar in a JDK image; the final image contains only a JRE and the jar.
- **Resilience:** the HTTP client has connect/read timeouts, and a failed call becomes a `502 Bad Gateway` instead of a stack trace.

## Tests

```bash
mvn verify
```

- `greeting-service`: controller test with MockMvc
- `client-service`: tests with `MockRestServiceServer` for both the success path and the downstream-failure path (502)

CI also runs `docker compose up` and checks that the request travels through both containers.

## Project structure

```
├── docker-compose.yml
├── pom.xml                    # aggregator: builds both services
├── greeting-service/
│   ├── Dockerfile
│   └── src/...
└── client-service/
    ├── Dockerfile
    └── src/...
```

Originally written in 2022 for a Docker course assignment, and later cleaned up.
