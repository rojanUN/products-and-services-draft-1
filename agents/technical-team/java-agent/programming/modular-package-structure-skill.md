# Modular Package Structure Skill

## Purpose

Keep packages consistent across all `sr-*` modules for navigability and code generation.

## Standard Structure

```
sr-<module-name>/
└── src/main/java/com/edx/platform/sr/<shortname>/
    ├── api/
    ├── application/
    ├── domain/
    │   ├── entity/
    │   ├── projection/
    │   └── repository/
    ├── infrastructure/
    │   ├── persistence/
    │   ├── adapter/
    │   └── event/
    ├── dto/
    │   ├── request/
    │   └── response/
    ├── mapper/
    ├── validator/
    └── config/
```

## Naming

- One aggregate root per primary entity cluster
- Suffix classes: `*Controller`, `*Service`, `*Repository`, `*Mapper`, `*Validator`
- Config: `*Configuration`, properties: `*Properties`

## Module Boundaries

- No circular dependencies between Gradle modules
- Cross-module calls via published client interfaces in `application` or dedicated `client` package

See [../module-map.md](../module-map.md).
