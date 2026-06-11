package com.swifttech.sr.ps.schema.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.builder.ServiceResponseBuilder;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.sr.ps.schema.entity.DynamicProductAttributeEntity;
import com.swifttech.sr.ps.schema.entity.ProductEntity;
import com.swifttech.sr.ps.schema.mapper.DynamicProductAttributeMapper;
import com.swifttech.sr.ps.schema.model.request.DynamicProductAttributeCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.DynamicProductAttributeResponse;
import com.swifttech.sr.ps.schema.repository.DynamicProductAttributeRepository;
import com.swifttech.sr.ps.schema.repository.ProductRepository;
import com.swifttech.sr.ps.schema.service.DynamicProductAttributeService;
import com.swifttech.sr.ps.utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DynamicProductAttributeServiceImpl implements DynamicProductAttributeService {

    private final DynamicProductAttributeRepository dynamicProductAttributeRepository;
    private final ProductRepository productRepository;

    @Override
    public GlobalResponse addDynamicAttribute(Long productId, DynamicProductAttributeCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        DynamicProductAttributeEntity entity = DynamicProductAttributeMapper.toEntity(request);
        entity.setProduct(product);
        DynamicProductAttributeEntity saved = Utility.handlePersist(entity, dynamicProductAttributeRepository);
        DynamicProductAttributeResponse response = DynamicProductAttributeMapper.toResponse(saved);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateDynamicAttribute(Long id, DynamicProductAttributeCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        DynamicProductAttributeEntity entity = dynamicProductAttributeRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        DynamicProductAttributeMapper.toUpdate(request, entity);
        Utility.handlePersist(entity, dynamicProductAttributeRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse deleteDynamicAttribute(Long id) throws GlobalException {
        DynamicProductAttributeEntity entity = dynamicProductAttributeRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        dynamicProductAttributeRepository.delete(entity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findDynamicAttributesByProductId(Long productId) throws GlobalException {
        List<DynamicProductAttributeEntity> attributes = dynamicProductAttributeRepository.findByProductUid(productId);
        List<DynamicProductAttributeResponse> responses = attributes.stream()
                .map(DynamicProductAttributeMapper::toResponse)
                .toList();
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), responses);
    }

}
