# sr-products-services-real Project Conventions

## Project Structure
```
src/main/java/com/swifttech/sr/ps/
  adapter/          -- Controllers (@ProductAndServicesRestController, maps /api/v2/products-services)
  annotation/       -- Custom annotations
  config/           -- OpenApiConfig
  entity/           -- JPA entities (16 total)
  enums/            -- Enums
  mapper/           -- Static mapper classes (NO MapStruct annotations, plain static methods)
  model/
    request/        -- Request DTOs
    response/       -- Response DTOs
  repository/       -- JPA repositories
  service/
    impl/           -- Service implementations
    specification/  -- JPA Specification classes for dynamic queries
  utils/            -- Utility.java, SpecificationHelper.java
```

## Build
- Gradle (build.gradle)
- Spring Boot 4.0.6, Java 21
- External JAR at `src/main/libs/edx-data-management-0.1.19-plain.jar`

## Entity Hierarchy (from JAR)
```
BaseEntity (abstract)   -- uid, version, createdAt, lastModifiedAt, createdBy, lastModifiedBy
  DomainMetaDataEntity  -- name, description (adds to BaseEntity)
    MasterEntity        -- status (StatusEnum) (adds to DomainMetaDataEntity)
```

## Naming Conventions

### Entities
- Class name: `{Name}Entity` (e.g., `BusinessEntityCategoryEntity`)
- Table name: `sr_{snake_case_name}` (e.g., `sr_business_entity_category`)

### Request DTOs
- Create/Update: `{Name}CreateUpdateRequest` (e.g., `BusinessEntityCategoryCreateUpdateRequest`)
- Paginated Listing: `{Name}DataRequest` where `{Name}` = entity class name minus "Entity" suffix (e.g., `BusinessEntityCategoryDataRequest`)
- All `*DataRequest` classes extend `com.swifttech.edx.dm.payload.request.PaginationRequest`
- Each `*DataRequest` includes at least: `private String searchText;`

### Specification Classes
- Location: `service/specification/` package
- Name: `{Name}Specification` (e.g., `BusinessEntityCategorySpecification`)
- Pattern: utility class with private constructor, static method `filterBy({Name}DataRequest)` returning `Specification<T>`
- Method signature takes the whole `DataRequest` object (not just searchText) so new filters can be added over time
- Uses `List<Predicate>` + `cb.and(predicates.toArray(...))` pattern — each filter appends a predicate
- For LIKE queries: uses `SpecificationHelper.likeIgnoreCase(cb, path, text)` from the shared utility
- Null-safe: wraps optional fields with `cb.coalesce(root.get("field"), "")`
- Case-insensitive: wraps with `cb.lower()`

### Repositories
- All extend `BaseRepository<T>` from JAR: `extends JpaRepository<T, Long>, JpaSpecificationExecutor<T>`
- Custom methods: `existsByUid(long)`, `existsByName(String)`, `existsByNameAndUidNot(String, Long)` (common pattern)

### Controllers
- Base path: `/api/v2/products-services` (from `@ProductAndServicesRestController`)
- Listing endpoint: `POST {entity-kebab-case}/list`
- Uses `@RequestBody {Name}DataRequest`
- Returns `ResponseEntity<GlobalResponse>`

### Service Interfaces & Implementations
- Interface: `{Name}Service` in `service/` package
- Implementation: `{Name}ServiceImpl` in `service/impl/` package
- Paginated method: `find{Name}PaginatedData({Name}DataRequest request)`
- Pattern:
```java
Specification<Entity> spec = EntitySpecification.filterBy(request);
Page<Entity> page = repository.findAll(spec, Helper.getPageable(request));
```

## Pagination
- `PaginationRequest` fields: `pageNo`, `pageSize`, `sortBy`, `direction`
- `Helper.getPageable(PaginationRequest)` converts to Spring `Pageable`
- Response: `DataPaginationResponse` (totalElementCount + result list)
- Wrapped in: `ServiceResponseBuilder.buildSuccessResponse(...)`

## SpecificationHelper Utility (`utils/SpecificationHelper.java`)
- Shared utility for reusable JPA CriteriaBuilder patterns
- `likePattern(String searchText)` → `"%searchText.lowercase%"`
- `likeIgnoreCase(CriteriaBuilder, Expression<String>, String)` → returns case-insensitive LIKE predicate
- Used by all Specification classes

## DRY Threshold Rule
Extract a shared utility only when the same code fragment is reused in ≥3 places. Single-use or duplicate-once patterns can remain inline.

## Mappers
- Plain utility classes with private constructor
- Static methods: `toResponse(Entity)`, `toEntity(Request)`, `toUpdate(Request, Entity)`

## Specification Search Fields per Entity
| Entity | Fields Searched |
|---|---|
| BusinessEntityCategoryEntity | name, description |
| CountryEntity | name, alpha2Code, alpha3Code, dialCode, numericCode |
| CurrencyClassificationEntity | name, description |
| CurrencyEntity | name, alphaThree, numeric, symbol |
| ProductAndServiceClassificationEntity | name, description |
| ProductClassificationEntity | name, description |
| ProductComponentEntity | name, description |
| ProductEntity | name, description |
| ProductTypeEntity | name, description |
| ServiceClassificationEntity | name, description |
| ServiceEntity | name (only) |
| ValueApplicationEntity | name, description |
| ValueComponentEntity | name, description |
| ValueComponentTypeEntity | name, description |
| ValueMovementEntity | name, description |

## Message Configuration
See [`MESSAGE_CONFIG.md`](../MESSAGE_CONFIG.md) for the full message code reference:
- Format: `{MODULE}-{SERVICE}-{METHOD}-{CODE}` (module code: `SPS`)
- Service & method code tables for all 16 services
- How message resolution works (AOP chain → properties files)
- Code range conventions and how to add new messages

## Key Imports
- `com.swifttech.edx.dm.payload.request.PaginationRequest`
- `com.swifttech.edx.dm.payload.response.DataPaginationResponse`
- `com.swifttech.edx.dm.payload.response.GlobalResponse`
- `com.swifttech.edx.dm.util.Helper`
- `com.swifttech.edx.dm.builder.ServiceResponseBuilder`
- `com.swifttech.edx.dm.exception.GlobalException`
- `com.swifttech.edx.dm.am.enums.ErrorCodeEnum`
- `com.swifttech.edx.dm.am.enums.SuccessCodeEnum`
- `com.swifttech.edx.dm.repository.BaseRepository`
- `io.micrometer.common.util.StringUtils`
- `com.swifttech.sr.ps.utils.SpecificationHelper`
