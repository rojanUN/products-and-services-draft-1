# Service Template

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class RemittanceService {

    private final RemittanceTransactionRepository repository;
    private final RemittanceMapper mapper;

    @Transactional
    public RemittanceResponse create(CreateRemittanceRequest request) {
        // BR-REM-001: validate business preconditions
        RemittanceTransaction entity = mapper.toEntity(request);
        entity.setStatus(RemittanceStatus.DRAFT);
        RemittanceTransaction saved = repository.save(entity);
        log.info("Remittance created id={}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public RemittancePageResponse list(int page, int size, String status) {
        Pageable pageable = PageRequest.of(page, size);
    Page<RemittanceSummaryProjection> result = repository.findByStatus(status, pageable);
    return mapper.toPageResponseFromProjection(result);
    }
}
```

Rules: `@Transactional` here; throw Platform exceptions; publish events after save when needed.
