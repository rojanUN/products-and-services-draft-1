# Test Generation Skill

## Minimum Tests

| Layer | Test type | Focus |
|-------|-----------|-------|
| Service | Unit (`@ExtendWith(MockitoExtension.class)`) | Business rules, exceptions |
| Validator | Unit | Valid/invalid cases |
| Controller | Slice / MockMvc | HTTP status, validation errors |
| Repository | `@DataJpaTest` | Custom queries (when complex) |

## Naming

`should{ExpectedBehavior}_when{Condition}`

## Rules

- Mock external adapters and repositories in service tests
- Cover happy path + primary error cases per requirement
- Tag or name tests with requirement ID in display name when useful

## Template

See [../templates/test-template.md](../templates/test-template.md).
