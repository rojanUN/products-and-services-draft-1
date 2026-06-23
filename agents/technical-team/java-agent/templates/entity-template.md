# Entity Template

```java
@Entity
@Table(name = "remittance_transaction")
@Getter
@Setter
public class RemittanceTransaction extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "beneficiary_id", nullable = false)
    private Long beneficiaryId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RemittanceStatus status;
}
```

Rules: extend `BaseEntity`; explicit column names; STRING enums.
