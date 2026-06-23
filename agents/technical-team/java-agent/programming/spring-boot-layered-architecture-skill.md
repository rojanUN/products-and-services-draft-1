# Spring Boot Layered Architecture Skill

## Package Layout

```
com.edx.platform.sr.<module>
├── api
├── application
├── domain
│   ├── entity
│   ├── projection
│   └── repository
├── infrastructure
│   ├── persistence
│   ├── adapter
│   └── event
├── dto
│   ├── request
│   └── response
├── mapper
├── validator
└── config
```

## Dependency Direction

```
api → application → domain ← infrastructure
```

- `domain` must not depend on `api` or Spring Web
- `infrastructure` implements `domain.repository` interfaces

## Controller Pattern

- Implement OpenAPI-generated interface when available
- Method body: validate → call service → map to response DTO → return ResponseEntity

## Service Pattern

- One service per aggregate or use-case cluster
- `@Transactional` on public service methods that write data
- Inject repository interfaces, mappers, validators, adapters

## Repository Pattern

- Interface in `domain.repository`
- JPA implementation in `infrastructure.persistence`
- Custom queries via `@Query` or Specification for complex filters
