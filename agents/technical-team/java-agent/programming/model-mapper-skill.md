# Model Mapper Skill

## Purpose

Map between entities, projections, and API models without exposing the persistence layer.

## Preferred: MapStruct

```java
@Mapper(componentModel = "spring")
public interface RemittanceMapper {
    RemittanceResponse toResponse(RemittanceTransaction entity);
    RemittanceTransaction toEntity(CreateRemittanceRequest request);
    RemittanceSummary toSummary(RemittanceSummaryProjection projection);
}
```

## Rules

- Separate request and response models (`model.request`, `model.response`)
- Mapper lives in `mapper` package
- Never map entity directly in controller inline
- Ignore sensitive/internal fields in response mapping
- Use `@Mapping(target = "...", ignore = true)` for read-only or computed fields

## Collection Mapping

- Map `Page<Projection>` to Platform `PageResponse<SummaryModel>` for list APIs ([projection-skill.md](projection-skill.md))
- Map `Page<Entity>` to Platform `PageResponse<ResponseModel>` only for detail-heavy list cases

## Projection Mapping

```java
RemittanceSummary toSummary(RemittanceSummaryProjection projection);

List<RemittanceSummary> toSummaryList(List<RemittanceSummaryProjection> projections);

default RemittancePageResponse toPageResponseFromProjection(Page<RemittanceSummaryProjection> page) {
    return new RemittancePageResponse(
        toSummaryList(page.getContent()),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements()
    );
}
```

## Manual Mapping

Acceptable only for trivial one-field mappings; prefer MapStruct for maintainability.

See [../code-generation/model-generation-skill.md](../code-generation/model-generation-skill.md) and [../code-generation/mapper-generation-skill.md](../code-generation/mapper-generation-skill.md).
