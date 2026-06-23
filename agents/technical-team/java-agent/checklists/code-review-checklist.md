# Code Review Checklist

- [ ] Layering: controller → service → repository
- [ ] No entities in API responses
- [ ] DTO validation present
- [ ] Business rules in service/domain, not controller
- [ ] Platform utilities reused
- [ ] No tenant logic in Product module
- [ ] Exceptions use Platform types and stable codes
- [ ] `@Transactional` on service writes only
- [ ] Tests cover happy path and key errors
- [ ] List APIs use projections where appropriate
- [ ] Traceability IDs in JavaDoc/OpenAPI

See [../review/review-skill.md](../review/review-skill.md).
