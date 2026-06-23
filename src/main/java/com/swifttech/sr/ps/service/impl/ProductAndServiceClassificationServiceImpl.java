package com.swifttech.sr.ps.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.util.MessageHelper;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.entity.ProductAndServiceClassificationEntity;
import com.swifttech.sr.ps.entity.ProductClassificationEntity;
import com.swifttech.sr.ps.entity.ServiceClassificationEntity;
import com.swifttech.sr.ps.entity.ValueComponentEntity;
import com.swifttech.sr.ps.mapper.ProductAndServiceClassificationMapper;
import com.swifttech.sr.ps.model.request.ProductAndServiceClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ProductAndServiceClassificationDataRequest;
import com.swifttech.sr.ps.model.response.ProductAndServiceClassificationResponse;
import com.swifttech.sr.ps.repository.ProductAndServiceClassificationRepository;
import com.swifttech.sr.ps.repository.ProductClassificationRepository;
import com.swifttech.sr.ps.repository.ServiceClassificationRepository;
import com.swifttech.sr.ps.repository.ValueComponentRepository;
import com.swifttech.sr.ps.service.ProductAndServiceClassificationService;
import com.swifttech.sr.ps.service.specification.ProductAndServiceClassificationSpecification;
import com.swifttech.sr.ps.utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductAndServiceClassificationServiceImpl implements ProductAndServiceClassificationService {

    private final ProductAndServiceClassificationRepository repository;
    private final ProductClassificationRepository productClassificationRepository;
    private final ServiceClassificationRepository serviceClassificationRepository;
    private final ValueComponentRepository valueComponentRepository;

    @Override
    public GlobalResponse createProductAndServiceClassification(ProductAndServiceClassificationCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        ProductAndServiceClassificationEntity entity = ProductAndServiceClassificationMapper.toEntity(request);
        attachRelations(request, entity);
        ProductAndServiceClassificationEntity saved = Utility.handlePersist(entity, repository);
        ProductAndServiceClassificationResponse response = ProductAndServiceClassificationMapper.toResponse(saved);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateProductAndServiceClassification(Long id, ProductAndServiceClassificationCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        ProductAndServiceClassificationEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ProductAndServiceClassificationMapper.toUpdate(request, entity);
        attachRelations(request, entity);
        Utility.handlePersist(entity, repository);
        return MessageHelper.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findProductAndServiceClassificationById(Long id) throws GlobalException {
        ProductAndServiceClassificationEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ProductAndServiceClassificationResponse response = ProductAndServiceClassificationMapper.toResponse(entity);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findProductAndServiceClassificationPaginatedData(ProductAndServiceClassificationDataRequest request) throws GlobalException {
        Specification<ProductAndServiceClassificationEntity> spec = ProductAndServiceClassificationSpecification.filterBy(request);
        Page<ProductAndServiceClassificationEntity> page = repository.findAll(spec, Helper.getPageable(request));
        List<ProductAndServiceClassificationResponse> responses = page.getContent().stream()
                .map(ProductAndServiceClassificationMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

    private void attachRelations(ProductAndServiceClassificationCreateUpdateRequest request, ProductAndServiceClassificationEntity entity) throws GlobalException {
        ProductClassificationEntity productClassification = productClassificationRepository.findById(request.getProductClassificationId())
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        entity.setProductClassification(productClassification);

        ServiceClassificationEntity serviceClassification = serviceClassificationRepository.findById(request.getServiceClassificationId())
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        entity.setServiceClassification(serviceClassification);

        if (request.getValueComponentIds() != null && !request.getValueComponentIds().isEmpty()) {
            Set<ValueComponentEntity> valueComponents = request.getValueComponentIds().stream()
                    .map(vcId -> {
                        try {
                            return valueComponentRepository.findById(vcId)
                                    .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
                        } catch (GlobalException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toSet());
            entity.setValueComponents(valueComponents);
        } else {
            entity.setValueComponents(Collections.emptySet());
        }
    }

}
