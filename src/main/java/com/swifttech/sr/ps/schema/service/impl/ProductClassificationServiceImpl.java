package com.swifttech.sr.ps.schema.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.builder.ServiceResponseBuilder;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.request.StatusUpdateRequest;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.schema.entity.ProductClassificationEntity;
import com.swifttech.sr.ps.schema.mapper.ProductClassificationMapper;
import com.swifttech.sr.ps.schema.model.request.ProductClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.ProductClassificationResponse;
import com.swifttech.sr.ps.schema.repository.ProductClassificationRepository;
import com.swifttech.sr.ps.schema.service.ProductClassificationService;
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
public class ProductClassificationServiceImpl implements ProductClassificationService {

    private final ProductClassificationRepository productClassificationRepository;

    @Override
    public GlobalResponse createProductClassification(ProductClassificationCreateUpdateRequest request) throws GlobalException {
        validateProductClassification(request);
        ProductClassificationEntity entity = ProductClassificationMapper.toEntity(request);
        attachParent(request, entity);
        ProductClassificationEntity saved = Utility.handlePersist(entity, productClassificationRepository);
        ProductClassificationResponse response = ProductClassificationMapper.toResponse(saved);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateProductClassification(Long id, ProductClassificationCreateUpdateRequest request) throws GlobalException {
        validateProductClassificationForUpdate(id, request);
        ProductClassificationEntity entity = findEntityById(id);
        ProductClassificationMapper.toUpdate(request, entity);
        attachParent(request, entity);
        Utility.handlePersist(entity, productClassificationRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse updateProductClassificationStatus(Long id, StatusUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        ProductClassificationEntity entity = findEntityById(id);
        entity.setStatus(request.getStatus());
        Utility.handlePersist(entity, productClassificationRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findProductClassificationById(Long id) throws GlobalException {
        ProductClassificationEntity entity = findEntityById(id);
        ProductClassificationResponse response = ProductClassificationMapper.toResponse(entity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findProductClassificationPaginatedData(PaginationRequest request) throws GlobalException {
        Page<ProductClassificationEntity> page = productClassificationRepository.findAll(Helper.getPageable(request));
        List<ProductClassificationResponse> responses = page.getContent().stream()
                .map(ProductClassificationMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

    private void validateProductClassification(ProductClassificationCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (productClassificationRepository.existsByName(request.getName())) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void validateProductClassificationForUpdate(Long id, ProductClassificationCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (productClassificationRepository.existsByNameAndUidNot(request.getName(), id)) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private ProductClassificationEntity findEntityById(Long id) throws GlobalException {
        return productClassificationRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
    }

    private void attachParent(ProductClassificationCreateUpdateRequest request, ProductClassificationEntity entity) throws GlobalException {
        if (request.getParentId() == null) {
            entity.setParentClassification(null);
            return;
        }
        ProductClassificationEntity parent = productClassificationRepository.findById(request.getParentId())
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        entity.setParentClassification(parent);
    }

}
