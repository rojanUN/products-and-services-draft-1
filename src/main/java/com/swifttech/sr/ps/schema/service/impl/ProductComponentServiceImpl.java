package com.swifttech.sr.ps.schema.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.builder.ServiceResponseBuilder;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.schema.entity.ProductComponentEntity;
import com.swifttech.sr.ps.schema.entity.ProductTypeEntity;
import com.swifttech.sr.ps.schema.mapper.ProductComponentMapper;
import com.swifttech.sr.ps.schema.model.request.ProductComponentCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.ProductComponentResponse;
import com.swifttech.sr.ps.schema.repository.ProductComponentRepository;
import com.swifttech.sr.ps.schema.repository.ProductTypeRepository;
import com.swifttech.sr.ps.schema.service.ProductComponentService;
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
public class ProductComponentServiceImpl implements ProductComponentService {

    private final ProductComponentRepository productComponentRepository;
    private final ProductTypeRepository productTypeRepository;

    @Override
    public GlobalResponse createProductComponent(ProductComponentCreateUpdateRequest request) throws GlobalException {
        validateProductComponent(request);
        ProductComponentEntity entity = ProductComponentMapper.toEntity(request);
        attachProductType(request, entity);
        ProductComponentEntity saved = Utility.handlePersist(entity, productComponentRepository);
        ProductComponentResponse response = ProductComponentMapper.toResponse(saved);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateProductComponent(Long id, ProductComponentCreateUpdateRequest request) throws GlobalException {
        validateProductComponentForUpdate(id, request);
        ProductComponentEntity entity = productComponentRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ProductComponentMapper.toUpdate(request, entity);
        attachProductType(request, entity);
        Utility.handlePersist(entity, productComponentRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findProductComponentById(Long id) throws GlobalException {
        ProductComponentEntity entity = productComponentRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ProductComponentResponse response = ProductComponentMapper.toResponse(entity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findProductComponentPaginatedData(PaginationRequest request) throws GlobalException {
        Page<ProductComponentEntity> page = productComponentRepository.findAll(Helper.getPageable(request));
        List<ProductComponentResponse> responses = page.getContent().stream()
                .map(ProductComponentMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

    private void validateProductComponent(ProductComponentCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (productComponentRepository.existsByName(request.getName())) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void validateProductComponentForUpdate(Long id, ProductComponentCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (productComponentRepository.existsByNameAndUidNot(request.getName(), id)) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void attachProductType(ProductComponentCreateUpdateRequest request, ProductComponentEntity entity) throws GlobalException {
        if (request.getProductTypeId() != null) {
            ProductTypeEntity productType = productTypeRepository.findById(request.getProductTypeId())
                    .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
            entity.setProductType(productType);
        } else {
            entity.setProductType(null);
        }
    }

}
