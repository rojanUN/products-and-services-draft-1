# Model Template

```java
// model.request
public record CreateRemittanceRequest(
    @NotNull Long customerId,
    @NotNull Long beneficiaryId,
    @NotNull @Positive BigDecimal amount,
    @NotBlank @Size(min = 3, max = 3) String currency
) {}

// model.response
public record RemittanceResponse(
    Long id,
    Long customerId,
    Long beneficiaryId,
    BigDecimal amount,
    String currency,
    RemittanceStatus status,
    Instant createdAt
) {}
```

Rules: validation on request models only; no JPA annotations; place in `model.request` / `model.response` packages.
