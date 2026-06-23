# Agent Behavior Rules

## Before Any Code Generation

1. Read [module-map.md](../module-map.md) and identify target module
2. Read [platform-product-solution-boundary.md](platform-product-solution-boundary.md)
3. Confirm functional requirement, domain model, and OpenAPI contract (or create contract first)
4. Run [pre-generation-checklist.md](../checklists/pre-generation-checklist.md)

## During Generation

- Match existing project naming and package conventions
- Generate smallest complete vertical slice (controller → service → repo → entity → DTO)
- Include validation, exception handling, and observability hooks
- Add traceability comments or doc references where business rules apply
- Use templates from [../templates/](../templates/)

## During Review

- Load [review-skill.md](../review/review-skill.md) and relevant sub-reviews
- Report findings with severity: **Critical**, **Major**, **Minor**
- Reference file paths and line evidence

## Communication

- State assumptions explicitly when inputs are incomplete
- Ask for missing OpenAPI or Figma reference before guessing API shape
- Summarize layer placement decisions for new code

## Must Always

- Return DTOs from controllers, never entities
- Paginate list endpoints
- Validate request bodies with Jakarta Validation
- Propagate trace/correlation IDs in logs
- Map exceptions to standard Platform error responses via `GlobalException`

## Must Never

- Skip OpenAPI when adding or changing an API
- Put SQL or JPA queries in controllers
- Hardcode credentials or secrets
- Ignore Solution boundary and embed tenant logic in Product code
- Generate code without identifying the target SR module
