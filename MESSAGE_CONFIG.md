# Message Configuration — sr-products-services (SPS)

## Code Format

```
{MODULE}-{SERVICE}-{METHOD}-{CODE}
```

Example: `SPS-PRO-CRE-001`

| Segment | Meaning | Example |
|---------|---------|---------|
| `{MODULE}` | Microservice module code | `SPS` |
| `{SERVICE}` | Service abbreviation (3 letters) | `PRO` (ProductService) |
| `{METHOD}` | Method/operation abbreviation (3 letters) | `CRE` (create) |
| `{CODE}` | Numeric code | `001` (error), `100` (success) |

## Module Code

Defined in `application.yml` under `message.management.moduleName`:

```yaml
message:
  management:
    moduleName:
      sr-products-services: SPS
```

Spring's `spring.application.name` = `sr-products-services` → resolved to `SPS`.

## Service Codes

Defined in `application.yml` under `message.management.serviceName`:

| Service Interface | Code |
|-------------------|------|
| BusinessEntityCategoryService | BEC |
| CountryService | CON |
| CurrencyClassificationService | CCL |
| CurrencyService | CUR |
| DynamicProductAttributeService | DPA |
| ProductAndServiceClassificationService | PSC |
| ProductClassificationService | PCL |
| ProductComponentService | PCO |
| ProductService | PRO |
| ProductTypeService | PTY |
| ServiceClassificationService | SCL |
| ServiceService | SRV |
| ValueApplicationService | VAP |
| ValueComponentService | VCO |
| ValueComponentTypeService | VCT |
| ValueMovementService | VMO |

## Method Codes

Defined in `application.yml` under `message.management.method`:

| Service | Method | Code |
|---------|--------|------|
| BusinessEntityCategoryService | createBusinessEntityCategory | CRE |
| | updateBusinessEntityCategory | UPD |
| | findBusinessEntityCategoryById | FID |
| | findBusinessEntityCategoryPaginatedData | PLS |
| CountryService | createCountry | CRE |
| | updateCountry | UPD |
| | findCountryById | FID |
| | findCountryPaginatedData | PLS |
| CurrencyClassificationService | createCurrencyClassification | CRE |
| | updateCurrencyClassification | UPD |
| | findCurrencyClassificationById | FID |
| | findCurrencyClassificationPaginatedData | PLS |
| CurrencyService | createCurrency | CRE |
| | updateCurrency | UPD |
| | updateCurrencyStatus | UST |
| | findCurrencyById | FID |
| | findCurrencyPaginatedData | PLS |
| DynamicProductAttributeService | createDynamicProductAttribute | CRE |
| | updateDynamicProductAttribute | UPD |
| | findDynamicProductAttributesByProductId | FID |
| ProductAndServiceClassificationService | createProductAndServiceClassification | CRE |
| | updateProductAndServiceClassification | UPD |
| | findProductAndServiceClassificationById | FID |
| | findProductAndServiceClassificationPaginatedData | PLS |
| ProductClassificationService | createProductClassification | CRE |
| | updateProductClassification | UPD |
| | updateProductClassificationStatus | UST |
| | findProductClassificationById | FID |
| | findProductClassificationPaginatedData | PLS |
| | findProductClassificationHierarchy | HIE |
| ProductComponentService | createProductComponent | CRE |
| | updateProductComponent | UPD |
| | findProductComponentById | FID |
| | findProductComponentPaginatedData | PLS |
| ProductService | createProduct | CRE |
| | updateProduct | UPD |
| | findProductById | FID |
| | findProductPaginatedData | PLS |
| ProductTypeService | createProductType | CRE |
| | updateProductType | UPD |
| | findProductTypeById | FID |
| | findProductTypePaginatedData | PLS |
| ServiceClassificationService | createServiceClassification | CRE |
| | updateServiceClassification | UPD |
| | updateServiceClassificationStatus | UST |
| | findServiceClassificationById | FID |
| | findServiceClassificationPaginatedData | PLS |
| | findServiceClassificationHierarchy | HIE |
| ServiceService | createService | CRE |
| | updateService | UPD |
| | findServiceById | FID |
| | findServicePaginatedData | PLS |
| ValueApplicationService | createValueApplication | CRE |
| | updateValueApplication | UPD |
| | findValueApplicationById | FID |
| | findValueApplicationPaginatedData | PLS |
| ValueComponentService | createValueComponent | CRE |
| | updateValueComponent | UPD |
| | updateValueComponentStatus | UST |
| | findValueComponentById | FID |
| | findValueComponentPaginatedData | PLS |
| ValueComponentTypeService | createValueComponentType | CRE |
| | updateValueComponentType | UPD |
| | findValueComponentTypeById | FID |
| | findValueComponentTypePaginatedData | PLS |
| ValueMovementService | createValueMovement | CRE |
| | updateValueMovement | UPD |
| | findValueMovementById | FID |
| | findValueMovementPaginatedData | PLS |

## Code Ranges

| Range | Type |
|-------|------|
| `-001` to `-099` | Error messages |
| `-100` to `-199` | Success messages |
| `-200` to `-299` | Validation messages |

Common error assignments across all services:
- `-001`: Empty/null request (create validation)
- `-002`: Referenced entity not found
- `-003`: Duplicate name exists
- `-004`: Inactive entity / business rule violation

## How Message Resolution Works

```
Java code                           Properties file lookup
───────                             ─────────────────────
ErrorCodeEnum._001.getMessage()     error.properties:
  ↓                                   SPS-PRO-CRE-001 = product cannot be ...
ResponseCodeAspect (AOP)
  ↓ builds key
  SPS-PRO-CRE-001
  ↓
MessageManagementProperties
  reads application.yml:
  moduleName.sr-products-services = SPS
  serviceName.ProductService = PRO
  method.ProductService.createProduct = CRE
  ↓
MessageBundle.getErrorMessage(key)
  → loads error.properties from classpath
  → returns: "product cannot be created with empty request."
```

The `ResponseCodeAspect` (in the JAR) intercepts `ErrorCodeEnum._XXX` and `SuccessCodeEnum._XXX` calls, builds the full `MODULE-SERVICE-METHOD-CODE` key at runtime using `MessageManagementProperties` (configured in `application.yml`), and passes it to `MessageBundle` which looks it up in the properties files.

## Properties Files

| File | Purpose |
|------|---------|
| `src/main/resources/error.properties` | Error messages for this module (SPS format) |
| `src/main/resources/success.properties` | Success messages for this module (SPS format) |
| `src/main/resources/templates/code-template` | Master reference for module/service/method code conventions |
| `src/main/resources/templates/error.properties` | Example error messages from other modules (USR, MST, TXN, RSP, CUS, ADM) |
| `src/main/resources/templates/success.properties` | Example success messages from other modules |

`error.properties` and `success.properties` at the classpath root (`src/main/resources/`) are loaded by the JAR's `MessageBundle`. The template files under `templates/` are reference only.

## Adding a New Message

1. **Add the method entry in `application.yml`** (if the method isn't already mapped):
   ```yaml
   method:
     YourService:
       yourNewMethod: YNM
   ```

2. **Add the properties entry** in the appropriate file:
   ```properties
   # error.properties
   SPS-XXX-YNM-001 = Descriptive error message.

   # success.properties
   SPS-XXX-YNM-100 = Operation completed successfully.
   ```

3. **Use in Java code**:
   ```java
   // Error
   throw new GlobalException(ErrorCodeEnum._001.getMessage());

   // Success
   return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), data);
   ```

   The `ErrorCodeEnum._001` / `SuccessCodeEnum._100` constants are resolved dynamically — the `ResponseCodeAspect` prepends the module, service, and method codes at runtime based on the current service context.

## Convention Reference

The master convention file at `src/main/resources/templates/code-template` defines the full YAML structure with example codes from all other microservice modules (AM/MST, RSP, TXN, USR, CUS, ADM). Use it as a reference when adding new services, methods, or modules.
