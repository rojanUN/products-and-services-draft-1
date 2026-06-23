# Mapper Template

```java
@Mapper(componentModel = "spring")
public interface RemittanceMapper {

    RemittanceResponse toResponse(RemittanceTransaction entity);

    RemittanceTransaction toEntity(CreateRemittanceRequest request);

    List<RemittanceResponse> toResponseList(List<RemittanceTransaction> entities);

    default RemittancePageResponse toPageResponse(Page<RemittanceTransaction> page) {
        return new RemittancePageResponse(
            toResponseList(page.getContent()),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements()
        );
    }
}
```

Rules: MapStruct; ignore audit fields on response mapping as needed.
