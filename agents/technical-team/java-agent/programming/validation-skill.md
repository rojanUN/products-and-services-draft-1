# Validation Skill

## Purpose

Apply Jakarta Validation and custom validators at API and domain boundaries.

## Layers

| Layer | Validation type |
|-------|-----------------|
| DTO | `@NotNull`, `@Size`, `@Pattern`, `@Valid` nested objects |
| Service | Business rule validation (uniqueness, state guards) |
| Custom | `@Constraint` validators for cross-field rules |

## Controller

```java
public ResponseEntity<RemittanceResponse> create(
    @Valid @RequestBody CreateRemittanceRequest request) {
```

## Custom Validator Pattern

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BeneficiaryAccountValidator.class)
public @interface ValidBeneficiaryAccount { }
```

## Messages

- Use message codes in validation range `200–299`: `AAA-BBB-OPR-2NN` — see [message-code-convention-skill.md](message-code-convention-skill.md)
- Use message keys for i18n: `{validation.RMS-RMS-CRE-201}`
- Align validation messages with Figma error text where specified

## Rules

- Validate input at API boundary
- Re-validate critical business rules in service (defense in depth)
- Do not rely on database constraints alone for user-facing errors

See [../code-generation/validator-generation-skill.md](../code-generation/validator-generation-skill.md) and [message-code-convention-skill.md](message-code-convention-skill.md).
