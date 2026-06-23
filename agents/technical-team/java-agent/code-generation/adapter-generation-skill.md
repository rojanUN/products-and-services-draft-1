# Adapter Generation Skill

## Purpose

Generate inbound REST adapters (controllers) that expose OpenAPI-defined APIs.

## Rules

- Implement OpenAPI interface when generated
- `@RestController` + `@RequestMapping` base path from spec
- Accept `@Valid` request models
- Return `ResponseEntity<ResponseModel>` with correct status
- No business logic — delegate to service
- Map service result via mapper

## Template

See [../templates/controller-template.md](../templates/controller-template.md).

## Checklist

- [ ] Matches OpenAPI operationId
- [ ] Correct HTTP status (201 for create)
- [ ] Pagination params on list endpoints
- [ ] Security annotation if required
