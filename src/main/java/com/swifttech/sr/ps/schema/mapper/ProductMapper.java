package com.swifttech.sr.ps.schema.mapper;

import com.swifttech.sr.ps.schema.entity.ProductEntity;
import com.swifttech.sr.ps.schema.model.request.ProductCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.ProductResponse;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ProductMapper {

    private ProductMapper() {
    }

    public static ProductResponse toResponse(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        ProductResponse response = new ProductResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setHasDynamicAttributes(entity.isHasDynamicAttributes());
        if (entity.getProductClassification() != null) {
            response.setProductClassificationId(entity.getProductClassification().getUid());
            response.setProductClassificationName(entity.getProductClassification().getName());
        }
        if (entity.getProductType() != null) {
            response.setProductTypeId(entity.getProductType().getUid());
            response.setProductTypeName(entity.getProductType().getName());
        }
        if (entity.getProductComponent() != null) {
            response.setProductComponentId(entity.getProductComponent().getUid());
            response.setProductComponentName(entity.getProductComponent().getName());
        }
        if (entity.getProductAndServiceClassification() != null) {
            response.setProductAndServiceClassificationId(entity.getProductAndServiceClassification().getUid());
            response.setProductAndServiceClassificationName(entity.getProductAndServiceClassification().getName());
        }
        if (entity.getService() != null) {
            response.setServiceId(entity.getService().getUid());
            response.setServiceName(entity.getService().getName());
        }
        if (entity.getDynamicProductAttributes() != null) {
            response.setDynamicAttributes(
                    entity.getDynamicProductAttributes().stream()
                            .map(DynamicProductAttributeMapper::toResponse)
                            .collect(Collectors.toSet())
            );
        }
        return response;
    }

    public static ProductEntity toEntity(ProductCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        return ProductEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .hasDynamicAttributes(request.isHasDynamicAttributes())
                .build();
    }

    public static void toUpdate(ProductCreateUpdateRequest request, ProductEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setHasDynamicAttributes(request.isHasDynamicAttributes());
    }

}
