package com.swifttech.sr.ps.mapper;

import com.swifttech.sr.ps.entity.BusinessEntityCategoryEntity;
import com.swifttech.sr.ps.model.request.BusinessEntityCategoryCreateUpdateRequest;
import com.swifttech.sr.ps.model.response.BusinessEntityCategoryResponse;

public final class BusinessEntityCategoryMapper {

    private BusinessEntityCategoryMapper() {
    }

    public static BusinessEntityCategoryResponse toResponse(BusinessEntityCategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        BusinessEntityCategoryResponse response = new BusinessEntityCategoryResponse();
        response.setId(entity.getUid());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        return response;
    }

    public static BusinessEntityCategoryEntity toEntity(BusinessEntityCategoryCreateUpdateRequest request) {
        if (request == null) {
            return null;
        }
        BusinessEntityCategoryEntity BusinessEntityCategoryEntity = new BusinessEntityCategoryEntity();
        BusinessEntityCategoryEntity.setName(request.getName());

        return BusinessEntityCategoryEntity;
    }

    public static void toUpdate(BusinessEntityCategoryCreateUpdateRequest request, BusinessEntityCategoryEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setName(request.getName());
    }

}
