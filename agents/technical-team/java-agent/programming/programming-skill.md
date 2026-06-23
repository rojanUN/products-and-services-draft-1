# Programming Skill

## Purpose

Master index for Java Spring Boot implementation patterns in Smart Remittance. Load sub-skills based on the task at hand.

## Standard Vertical Slice

When implementing a feature, generate in this order:

1. OpenAPI spec update ([../openapi/openapi-first-skill.md](../openapi/openapi-first-skill.md))
2. Entity + repository + projection for list APIs ([spring-data-jpa-skill.md](spring-data-jpa-skill.md), [projection-skill.md](projection-skill.md))
3. Models + mapper ([model-mapper-skill.md](model-mapper-skill.md), [../code-generation/model-generation-skill.md](../code-generation/model-generation-skill.md))
4. Validator ([validation-skill.md](validation-skill.md))
5. Service ([../code-generation/service-generation-skill.md](../code-generation/service-generation-skill.md))
6. Inbound adapter / controller ([../code-generation/adapter-generation-skill.md](../code-generation/adapter-generation-skill.md))
7. Events/adapters if needed
8. Tests ([../code-generation/test-generation-skill.md](../code-generation/test-generation-skill.md))

## Layer Rules

| Layer | Package suffix | Annotations allowed |
|-------|----------------|---------------------|
| API | `.api` | `@RestController`, `@RequestMapping`, validation on DTOs |
| Application | `.application` | `@Service`, `@Transactional` |
| Domain | `.domain` | JPA entity annotations on entities only |
| Infrastructure | `.infrastructure` | `@Repository`, adapter implementations |

## Sub-Skills

| Topic | File |
|-------|------|
| Layered architecture | [spring-boot-layered-architecture-skill.md](spring-boot-layered-architecture-skill.md) |
| Platform standards | [platform-standard-usage-skill.md](platform-standard-usage-skill.md) |
| Product design | [product-layer-design-skill.md](product-layer-design-skill.md) |
| Solution mapping | [solution-layer-mapping-skill.md](solution-layer-mapping-skill.md) |
| Runtime switching | [service-runtime-switching-skill.md](service-runtime-switching-skill.md) |
| Client adapters | [../code-generation/adapter-design-pattern-skill.md](../code-generation/adapter-design-pattern-skill.md) |
| JPA | [spring-data-jpa-skill.md](spring-data-jpa-skill.md) |
| Projections | [projection-skill.md](projection-skill.md) |
| Model / mapper | [model-mapper-skill.md](model-mapper-skill.md) |
| Validation | [validation-skill.md](validation-skill.md) |
| Exceptions | [exception-handling-skill.md](exception-handling-skill.md) |
| Message codes | [message-code-convention-skill.md](message-code-convention-skill.md) |
| Security | [security-skill.md](security-skill.md) |
| Observability | [observability-skill.md](observability-skill.md) |
| Metrics | [metrics-monitoring-skill.md](metrics-monitoring-skill.md) |
| Performance | [performance-skill.md](performance-skill.md) |
| Events | [event-driven-skill.md](event-driven-skill.md) |
| Jasper reporting | [jasper-reporting-skill.md](jasper-reporting-skill.md) |
| Camel routes | [camel-route-skill.md](camel-route-skill.md) |
| Package structure | [modular-package-structure-skill.md](modular-package-structure-skill.md) |

## Core Principles

- Controllers delegate to services; services own transactions
- Never expose JPA entities in API responses
- Reuse Platform utilities; do not duplicate
- Solution-specific behavior belongs in Solution layer or runtime strategies
