package com.swifttech.sr.ps.mapper;

import com.swifttech.sr.ps.entity.ProductAndServiceClassificationEntity;
import com.swifttech.sr.ps.model.request.ProductAndServiceClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.model.response.ProductAndServiceClassificationResponse;

public final class ProductAndServiceClassificationMapper {

    private ProductAndServiceClassificationMapper() {
    }

    public static ProductAndServiceClassificationResponse toResponse(ProductAndServiceClassificationEntity entity) {
        if (entity == null) {
            return null;
        }
        ProductAndServiceClassificationResponse response = new ProductAndServiceClassificationResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        if (entity.getProductClassification() != null) {
            response.setProductClassificationId(entity.getProductClassification().getUid());
            response.setProductClassificationName(entity.getProductClassification().getName());
        }
        if (entity.getServiceClassification() != null) {
            response.setServiceClassificationId(entity.getServiceClassification().getUid());
            response.setServiceClassificationName(entity.getServiceClassification().getName());
        }
        return response;
    }

    public static ProductAndServiceClassificationEntity toEntity(ProductAndServiceClassificationCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        return ProductAndServiceClassificationEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public static void toUpdate(ProductAndServiceClassificationCreateUpdateRequest request, ProductAndServiceClassificationEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
    }

}
