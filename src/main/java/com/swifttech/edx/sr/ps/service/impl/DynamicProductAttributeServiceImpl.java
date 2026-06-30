package com.swifttech.edx.sr.ps.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.util.MessageHelper;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.sr.ps.entity.DynamicProductAttributeEntity;
import com.swifttech.edx.sr.ps.entity.ProductEntity;
import com.swifttech.edx.sr.ps.mapper.DynamicProductAttributeMapper;
import com.swifttech.edx.sr.ps.model.request.DynamicProductAttributeCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.response.DynamicProductAttributeResponse;
import com.swifttech.edx.sr.ps.repository.DynamicProductAttributeRepository;
import com.swifttech.edx.sr.ps.repository.ProductRepository;
import com.swifttech.edx.sr.ps.service.DynamicProductAttributeService;
import com.swifttech.edx.sr.ps.utils.Utility;
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
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
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
        return MessageHelper.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse deleteDynamicAttribute(Long id) throws GlobalException {
        DynamicProductAttributeEntity entity = dynamicProductAttributeRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        dynamicProductAttributeRepository.delete(entity);
        return MessageHelper.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findDynamicAttributesByProductId(Long productId) throws GlobalException {
        List<DynamicProductAttributeEntity> attributes = dynamicProductAttributeRepository.findByProductUid(productId);
        List<DynamicProductAttributeResponse> responses = attributes.stream()
                .map(DynamicProductAttributeMapper::toResponse)
                .toList();
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), responses);
    }

}
