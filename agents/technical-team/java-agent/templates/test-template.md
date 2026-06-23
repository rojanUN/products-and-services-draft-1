# Test Template

```java
@ExtendWith(MockitoExtension.class)
class RemittanceServiceTest {

    @Mock RemittanceTransactionRepository repository;
    @Mock RemittanceMapper mapper;
    @InjectMocks RemittanceService service;

    @Test
    void shouldCreateRemittance_whenRequestValid() {
        CreateRemittanceRequest request = new CreateRemittanceRequest(
            1L, 2L, BigDecimal.TEN, "USD");
        RemittanceTransaction entity = new RemittanceTransaction();
        RemittanceResponse response = new RemittanceResponse(/* ... */);

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        RemittanceResponse result = service.create(request);

        assertThat(result).isEqualTo(response);
        verify(repository).save(entity);
    }
}
```

Rules: unit test service logic; MockMvc for controller; name tests with behavior + condition.
