package com.swifttech.edx.sr.ps.mapper;

import com.swifttech.edx.sr.ps.entity.ProductTypeEntity;
import com.swifttech.edx.sr.ps.model.request.ProductTypeCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.response.ProductTypeResponse;

public final class ProductTypeMapper {

    private ProductTypeMapper() {
    }

    public static ProductTypeResponse toResponse(ProductTypeEntity entity) {
        if (entity == null) {
            return null;
        }
        ProductTypeResponse response = new ProductTypeResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        if (entity.getProductClassification() != null) {
            response.setProductClassificationId(entity.getProductClassification().getUid());
            response.setProductClassificationName(entity.getProductClassification().getName());
        }
        return response;
    }

    public static ProductTypeEntity toEntity(ProductTypeCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        return ProductTypeEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public static void toUpdate(ProductTypeCreateUpdateRequest request, ProductTypeEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
    }

}
