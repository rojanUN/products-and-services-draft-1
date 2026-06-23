# Validator Generation Skill

## Rules

- Class-level `@Constraint` for cross-field validation
- Implement `ConstraintValidator<Annotation, DTO>`
- Inject repositories only when needed for uniqueness checks
- Return clear default messages aligned with Figma copy

## Template

See [../templates/validator-template.md](../templates/validator-template.md).
