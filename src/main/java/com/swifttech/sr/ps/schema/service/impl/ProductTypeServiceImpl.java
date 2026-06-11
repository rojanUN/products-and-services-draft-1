package com.swifttech.sr.ps.schema.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.builder.ServiceResponseBuilder;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.schema.entity.ProductClassificationEntity;
import com.swifttech.sr.ps.schema.entity.ProductTypeEntity;
import com.swifttech.sr.ps.schema.mapper.ProductTypeMapper;
import com.swifttech.sr.ps.schema.model.request.ProductTypeCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.ProductTypeResponse;
import com.swifttech.sr.ps.schema.repository.ProductClassificationRepository;
import com.swifttech.sr.ps.schema.repository.ProductTypeRepository;
import com.swifttech.sr.ps.schema.service.ProductTypeService;
import com.swifttech.sr.ps.utils.Utility;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {

    private final ProductTypeRepository productTypeRepository;
    private final ProductClassificationRepository productClassificationRepository;

    @Override
    public GlobalResponse createProductType(ProductTypeCreateUpdateRequest request) throws GlobalException {
        validateProductType(request);
        ProductTypeEntity entity = ProductTypeMapper.toEntity(request);
        attachClassification(request, entity);
        ProductTypeEntity saved = Utility.handlePersist(entity, productTypeRepository);
        ProductTypeResponse response = ProductTypeMapper.toResponse(saved);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateProductType(Long id, ProductTypeCreateUpdateRequest request) throws GlobalException {
        validateProductTypeForUpdate(id, request);
        ProductTypeEntity entity = productTypeRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ProductTypeMapper.toUpdate(request, entity);
        attachClassification(request, entity);
        Utility.handlePersist(entity, productTypeRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findProductTypeById(Long id) throws GlobalException {
        ProductTypeEntity entity = productTypeRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ProductTypeResponse response = ProductTypeMapper.toResponse(entity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findProductTypePaginatedData(PaginationRequest request) throws GlobalException {
        Page<ProductTypeEntity> page = productTypeRepository.findAll(Helper.getPageable(request));
        List<ProductTypeResponse> responses = page.getContent().stream()
                .map(ProductTypeMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

    private void validateProductType(ProductTypeCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (productTypeRepository.existsByName(request.getName())) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void validateProductTypeForUpdate(Long id, ProductTypeCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (productTypeRepository.existsByNameAndUidNot(request.getName(), id)) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void attachClassification(ProductTypeCreateUpdateRequest request, ProductTypeEntity entity) throws GlobalException {
        ProductClassificationEntity classification = productClassificationRepository.findById(request.getProductClassificationId())
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        entity.setProductClassification(classification);
    }

}
