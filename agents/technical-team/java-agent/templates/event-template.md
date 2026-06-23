# Event Template

```java
public record RemittanceSubmittedEvent(
    Long remittanceId,
    Long customerId,
    String status,
    Instant occurredAt
) {}

@Component
@RequiredArgsConstructor
public class RemittanceEventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publishSubmitted(RemittanceTransaction entity) {
        publisher.publishEvent(new RemittanceSubmittedEvent(
            entity.getId(),
            entity.getCustomerId(),
            entity.getStatus().name(),
            Instant.now()
        ));
    }
}

@Component
@Slf4j
public class RemittanceSubmittedHandler {

    @EventListener
    public void on(RemittanceSubmittedEvent event) {
        log.info("Handling RemittanceSubmitted remittanceId={}", event.remittanceId());
    }
}
```

Rules: publish after successful transaction; minimal payload; idempotent consumers.
