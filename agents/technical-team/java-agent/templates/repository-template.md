# Repository Template

```java
// domain/repository
public interface RemittanceTransactionRepository
        extends JpaRepository<RemittanceTransaction, Long> {

    Page<RemittanceSummaryProjection> findByStatus(String status, Pageable pageable);

    Optional<RemittanceTransaction> findByIdAndCustomerId(Long id, Long customerId);
}
```

```java
// infrastructure/persistence — only if custom impl needed beyond Spring Data
@Repository
public interface RemittanceTransactionJpaRepository
        extends RemittanceTransactionRepository {
}
```

Rules: interface in domain; no business logic; paginate list queries.
