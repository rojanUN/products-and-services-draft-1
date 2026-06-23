# JPA Review

## Check

- [ ] Entity table and column names explicit
- [ ] Appropriate indexes for query filters
- [ ] Lazy loading; no unintended EAGER collections
- [ ] `@Transactional` on service, not repository
- [ ] No N+1 in list endpoints
- [ ] List APIs use projections when returning summary fields only
- [ ] Projection fields align with OpenAPI summary schema
- [ ] Soft delete pattern consistent with module
- [ ] Enum stored as STRING

## Violations

| Finding | Severity |
|---------|----------|
| Unpaginated `findAll` in API path | Critical |
| `Page<Entity>` used for list API with summary fields only | Major |
| Missing `@Version` on concurrent update entity | Major |
| Business logic in `@Query` repository | Minor |

See [../programming/spring-data-jpa-skill.md](../programming/spring-data-jpa-skill.md).
