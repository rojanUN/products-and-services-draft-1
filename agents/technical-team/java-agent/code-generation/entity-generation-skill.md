# Entity Generation Skill

## Rules

- JPA entity in `domain.entity`
- Extend Platform `BaseEntity` when applicable
- Explicit `@Table`, `@Column` for non-default names
- `Long` `@Id` with `@GeneratedValue` or assigned ID strategy per module standard
- Relationships with correct cascade and fetch type
- Status as `@Enumerated(EnumType.STRING)`

## Template

See [../templates/entity-template.md](../templates/entity-template.md).
