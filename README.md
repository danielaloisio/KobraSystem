# KobraSystem

A sample Spring Boot project demonstrating **Clean Architecture** principles with Java 21 and PostgreSQL.

## About

KobraSystem is a reference example of how to structure a Spring Boot application following Clean Architecture, separating concerns into well-defined layers that keep the domain isolated from frameworks and infrastructure details.

## Tech Stack

- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **PostgreSQL**
- **Docker / Docker Compose**
- **JUnit 5 + Mockito + AssertJ**

## Architecture

The project is organized into four layers:

```
org.kobra
├── domain/              # Core business logic — no Spring, no JPA
│   ├── entity/          # Domain entities (UUID as primary key)
│   └── repository/      # Repository interfaces (contracts)
├── application/
│   └── usecase/         # Use cases — orchestrate domain rules
├── infrastructure/
│   └── persistence/     # JPA entities + repository implementations
└── adapter/
    ├── controller/      # REST controllers
    └── dto/             # Request / Response records
```

### Dependency rule

Dependencies always point inward. The `domain` layer knows nothing about Spring, JPA, or any external framework. Use cases depend only on domain interfaces, and infrastructure implements those interfaces.

```
adapter → application → domain ← infrastructure
```

## Getting Started

### Prerequisites

- Java 21
- Docker

### Run the database

```bash
docker-compose up -d
```

### Run the application

```bash
./run.sh
```

The application starts at `http://localhost:8080`.

### Run the tests

```bash
./run-tests.sh
```

## API

### Products

| Method | Endpoint        | Description          |
|--------|-----------------|----------------------|
| POST   | /products       | Create a product     |
| GET    | /products       | List all products    |
| GET    | /products/{id}  | Find product by UUID |

```bash
# Create
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Notebook", "price": 3500.00}'

# List all
curl http://localhost:8080/products

# Find by ID
curl http://localhost:8080/products/550e8400-e29b-41d4-a716-446655440000
```