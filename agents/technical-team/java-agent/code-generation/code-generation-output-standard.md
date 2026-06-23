# Code Generation Output Standard

## Purpose

Define consistent structure and quality bar for all generated Java code.

## Output Package

For each feature, deliver:

1. OpenAPI YAML diff or new file
2. Entity + repository + projection (for list APIs)
3. Request/response models
4. Mapper interface
5. Validator(s) if needed
6. Service interface + implementation
7. Inbound adapter (controller)
8. Event classes + publisher/handler if applicable
9. Outbound adapter if external integration
10. Unit tests for service and validator
11. Traceability matrix snippet

## Code Quality Bar

- Constructor injection only
- No wildcard imports
- JavaDoc on public service methods with requirement ID
- Follow module package structure
- Use Platform base types

## Templates

Use files in [../templates/](../templates/) — do not invent alternate patterns without justification.

## Sub-Skills

See `code-generation/*-generation-skill.md` for layer-specific rules.
