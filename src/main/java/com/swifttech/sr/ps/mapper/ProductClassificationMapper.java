package com.swifttech.sr.ps.mapper;

import com.swifttech.edx.dm.enums.StatusEnum;
import com.swifttech.sr.ps.entity.ProductClassificationEntity;
import com.swifttech.sr.ps.model.request.ProductClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.model.response.ProductClassificationResponse;

public final class ProductClassificationMapper {

    private ProductClassificationMapper() {
    }

    public static ProductClassificationResponse toResponse(ProductClassificationEntity entity) {
        if (entity == null) {
            return null;
        }
        ProductClassificationResponse response = new ProductClassificationResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setStatus(entity.getStatus());
        if (entity.getParentClassification() != null) {
            response.setParentId(entity.getParentClassification().getUid());
            response.setParentName(entity.getParentClassification().getName());
        }
        return response;
    }

    public static ProductClassificationEntity toEntity(ProductClassificationCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        return ProductClassificationEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(StatusEnum.ACTIVE)
                .build();
    }

    public static void toUpdate(ProductClassificationCreateUpdateRequest request, ProductClassificationEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
    }

}
