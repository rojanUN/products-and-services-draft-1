package com.swifttech.sr.ps.schema.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.builder.ServiceResponseBuilder;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.schema.entity.*;
import com.swifttech.sr.ps.schema.mapper.ServiceMapper;
import com.swifttech.sr.ps.schema.model.request.ServiceCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.ServiceResponse;
import com.swifttech.sr.ps.schema.repository.*;
import com.swifttech.sr.ps.schema.service.ServiceService;
import com.swifttech.sr.ps.utils.Utility;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final BusinessEntityCategoryRepository businessEntityCategoryRepository;
    private final ServiceClassificationRepository serviceClassificationRepository;
    private final ValueComponentRepository valueComponentRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public GlobalResponse createService(ServiceCreateUpdateRequest request) throws GlobalException {
        validateService(request);
        ServiceEntity entity = ServiceMapper.toEntity(request);
        attachRelations(request, entity);
        attachValueComponents(request, entity);
        ServiceEntity saved = Utility.handlePersist(entity, serviceRepository);
        linkProducts(request, saved);
        ServiceResponse response = ServiceMapper.toResponse(saved);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    @Transactional
    public GlobalResponse updateService(Long id, ServiceCreateUpdateRequest request) throws GlobalException {
        validateServiceForUpdate(id, request);
        ServiceEntity entity = serviceRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ServiceMapper.toUpdate(request, entity);
        attachRelations(request, entity);
        attachValueComponents(request, entity);
        linkProducts(request, entity);
        Utility.handlePersist(entity, serviceRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findServiceById(Long id) throws GlobalException {
        ServiceEntity entity = serviceRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ServiceResponse response = ServiceMapper.toResponse(entity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findServicePaginatedData(PaginationRequest request) throws GlobalException {
        Page<ServiceEntity> page = serviceRepository.findAll(Helper.getPageable(request));
        List<ServiceResponse> responses = page.getContent().stream()
                .map(ServiceMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

    private void validateService(ServiceCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (serviceRepository.existsByName(request.getName())) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void validateServiceForUpdate(Long id, ServiceCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (serviceRepository.existsByNameAndUidNot(request.getName(), id)) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void attachRelations(ServiceCreateUpdateRequest request, ServiceEntity entity) throws GlobalException {
        if (request.getServiceCategoryId() != null) {
            BusinessEntityCategoryEntity category = businessEntityCategoryRepository.findById(request.getServiceCategoryId())
                    .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
            entity.setServiceCategory(category);
        } else {
            entity.setServiceCategory(null);
        }

        ServiceClassificationEntity classification = serviceClassificationRepository.findById(request.getServiceClassificationId())
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        entity.setServiceClassification(classification);
    }

    private void attachValueComponents(ServiceCreateUpdateRequest request, ServiceEntity entity) {
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

    private void linkProducts(ServiceCreateUpdateRequest request, ServiceEntity entity) {
        if (request.getProductIds() != null && !request.getProductIds().isEmpty()) {
            Set<ProductEntity> products = request.getProductIds().stream()
                    .map(pId -> {
                        try {
                            return productRepository.findById(pId)
                                    .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
                        } catch (GlobalException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toSet());
            products.forEach(p -> p.setService(entity));
            productRepository.saveAll(products);
        }
    }

}
