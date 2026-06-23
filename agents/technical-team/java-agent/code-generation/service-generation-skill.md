# Service Generation Skill

## Rules

- `@Service` class in `application` package
- `@Transactional` on write methods
- Inject repository, mapper, validator, adapters
- Enforce business rules and state transitions
- Publish events after successful commit
- Throw Platform exceptions with stable codes

## Template

See [../templates/service-template.md](../templates/service-template.md).

## Method Structure

1. Validate input / state preconditions
2. Load entities
3. Apply business logic
4. Persist
5. Publish events
6. Return mapped DTO
