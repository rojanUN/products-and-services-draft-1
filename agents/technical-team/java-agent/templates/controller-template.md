# Controller Template

```java
@RestController
@RequestMapping("/api/v1/remittances")
@RequiredArgsConstructor
public class RemittanceController implements RemittanceApi {

    private final RemittanceService remittanceService;

    @Override
    public ResponseEntity<RemittanceResponse> createRemittance(
            @Valid @RequestBody CreateRemittanceRequest request) {
        RemittanceResponse response = remittanceService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<RemittancePageResponse> listRemittances(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(remittanceService.list(page, size, status));
    }
}
```

Rules: no business logic; implement OpenAPI interface; use `@Valid` on requests.
