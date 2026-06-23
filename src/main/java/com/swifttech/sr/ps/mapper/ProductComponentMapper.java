package com.swifttech.sr.ps.mapper;

import com.swifttech.sr.ps.entity.ProductComponentEntity;
import com.swifttech.sr.ps.model.request.ProductComponentCreateUpdateRequest;
import com.swifttech.sr.ps.model.response.ProductComponentResponse;

public final class ProductComponentMapper {

    private ProductComponentMapper() {
    }

    public static ProductComponentResponse toResponse(ProductComponentEntity entity) {
        if (entity == null) {
            return null;
        }
        ProductComponentResponse response = new ProductComponentResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        if (entity.getProductType() != null) {
            response.setProductTypeId(entity.getProductType().getUid());
            response.setProductTypeName(entity.getProductType().getName());
        }
        return response;
    }

    public static ProductComponentEntity toEntity(ProductComponentCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        return ProductComponentEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public static void toUpdate(ProductComponentCreateUpdateRequest request, ProductComponentEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
    }

}
