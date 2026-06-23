package com.swifttech.sr.ps.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.util.MessageHelper;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.entity.ProductComponentEntity;
import com.swifttech.sr.ps.entity.ProductTypeEntity;
import com.swifttech.sr.ps.mapper.ProductComponentMapper;
import com.swifttech.sr.ps.model.request.ProductComponentCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ProductComponentDataRequest;
import com.swifttech.sr.ps.model.response.ProductComponentResponse;
import com.swifttech.sr.ps.repository.ProductComponentRepository;
import com.swifttech.sr.ps.repository.ProductTypeRepository;
import com.swifttech.sr.ps.service.ProductComponentService;
import com.swifttech.sr.ps.service.specification.ProductComponentSpecification;
import com.swifttech.sr.ps.utils.Utility;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
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
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateProductComponent(Long id, ProductComponentCreateUpdateRequest request) throws GlobalException {
        validateProductComponentForUpdate(id, request);
        ProductComponentEntity entity = productComponentRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ProductComponentMapper.toUpdate(request, entity);
        attachProductType(request, entity);
        Utility.handlePersist(entity, productComponentRepository);
        return MessageHelper.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findProductComponentById(Long id) throws GlobalException {
        ProductComponentEntity entity = productComponentRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ProductComponentResponse response = ProductComponentMapper.toResponse(entity);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findProductComponentPaginatedData(ProductComponentDataRequest request) throws GlobalException {
        Specification<ProductComponentEntity> spec = ProductComponentSpecification.filterBy(request);
        Page<ProductComponentEntity> page = productComponentRepository.findAll(spec, Helper.getPageable(request));
        List<ProductComponentResponse> responses = page.getContent().stream()
                .map(ProductComponentMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
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
