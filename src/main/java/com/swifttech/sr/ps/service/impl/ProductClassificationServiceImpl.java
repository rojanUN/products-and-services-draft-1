package com.swifttech.sr.ps.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.builder.ServiceResponseBuilder;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.request.StatusUpdateRequest;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.entity.ProductClassificationEntity;
import com.swifttech.sr.ps.mapper.ProductClassificationMapper;
import com.swifttech.sr.ps.model.request.ProductClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.model.response.ProductClassificationResponse;
import com.swifttech.sr.ps.repository.ProductClassificationRepository;
import com.swifttech.sr.ps.service.ProductClassificationService;
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
        ProductClassificationEntity productClassificationEntity = ProductClassificationMapper.toEntity(request);
        attachParent(request, productClassificationEntity);
        ProductClassificationEntity savedProductClassificationEntity = Utility.handlePersist(productClassificationEntity, productClassificationRepository);
        ProductClassificationResponse productClassificationResponse = ProductClassificationMapper.toResponse(savedProductClassificationEntity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), productClassificationResponse);
    }

    @Override
    public GlobalResponse updateProductClassification(Long id, ProductClassificationCreateUpdateRequest request) throws GlobalException {
        validateProductClassificationForUpdate(id, request);
        ProductClassificationEntity productClassificationEntity = findProductClassificationEntityById(id);
        ProductClassificationMapper.toUpdate(request, productClassificationEntity);
        attachParent(request, productClassificationEntity);
        Utility.handlePersist(productClassificationEntity, productClassificationRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse updateProductClassificationStatus(Long id, StatusUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        ProductClassificationEntity productClassificationEntity = findProductClassificationEntityById(id);
        productClassificationEntity.setStatus(request.getStatus());
        Utility.handlePersist(productClassificationEntity, productClassificationRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findProductClassificationById(Long id) throws GlobalException {
        ProductClassificationEntity productClassificationEntity = findProductClassificationEntityById(id);
        ProductClassificationResponse productClassificationResponse = ProductClassificationMapper.toResponse(productClassificationEntity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), productClassificationResponse);
    }

    @Override
    public GlobalResponse findProductClassificationPaginatedData(PaginationRequest request) throws GlobalException {
        Page<ProductClassificationEntity> productClassificationPage = productClassificationRepository.findAll(Helper.getPageable(request));
        List<ProductClassificationResponse> productClassificationResponses = productClassificationPage.getContent().stream()
                .map(ProductClassificationMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(productClassificationPage.getTotalElements(), productClassificationResponses);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

    //Helpers
    private void validateProductClassification(ProductClassificationCreateUpdateRequest request) throws GlobalException {

        //request null validation
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }

        //same name validatory
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

    private ProductClassificationEntity findProductClassificationEntityById(Long id) throws GlobalException {
        return productClassificationRepository.findById(id).orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
    }


    //attach parent if available
    private void attachParent(ProductClassificationCreateUpdateRequest request, ProductClassificationEntity entity) throws GlobalException {
        if (request.getParentId() == null) {
            entity.setParentProductClassification(null);
            return;
        }
        ProductClassificationEntity parent = productClassificationRepository.findById(request.getParentId()).orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        entity.setParentProductClassification(parent);
    }

}
