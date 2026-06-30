package com.swifttech.edx.sr.ps.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.util.MessageHelper;
import com.swifttech.edx.dm.enums.StatusEnum;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.edx.sr.ps.entity.BusinessEntityCategoryEntity;
import com.swifttech.edx.sr.ps.entity.ProductEntity;
import com.swifttech.edx.sr.ps.entity.ServiceClassificationEntity;
import com.swifttech.edx.sr.ps.entity.ServiceEntity;
import com.swifttech.edx.sr.ps.entity.ValueComponentEntity;
import com.swifttech.edx.sr.ps.repository.BusinessEntityCategoryRepository;
import com.swifttech.edx.sr.ps.repository.ProductRepository;
import com.swifttech.edx.sr.ps.repository.ServiceClassificationRepository;
import com.swifttech.edx.sr.ps.repository.ServiceRepository;
import com.swifttech.edx.sr.ps.repository.ValueComponentRepository;
import com.swifttech.edx.sr.ps.mapper.ServiceMapper;
import com.swifttech.edx.sr.ps.model.request.ServiceCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.ServiceDataRequest;
import com.swifttech.edx.sr.ps.model.response.ServiceResponse;
import com.swifttech.edx.sr.ps.service.ServiceService;
import com.swifttech.edx.sr.ps.service.specification.ServiceSpecification;
import com.swifttech.edx.sr.ps.utils.Utility;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
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
        attachProducts(request, entity);
        ServiceEntity saved = Utility.handlePersist(entity, serviceRepository);
        ServiceResponse response = ServiceMapper.toResponse(saved);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
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
        attachProducts(request, entity);
        Utility.handlePersist(entity, serviceRepository);
        return MessageHelper.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findServiceById(Long id) throws GlobalException {
        ServiceEntity entity = serviceRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ServiceResponse response = ServiceMapper.toResponse(entity);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findServicePaginatedData(ServiceDataRequest request) throws GlobalException {
        Specification<ServiceEntity> spec = ServiceSpecification.filterBy(request);
        Page<ServiceEntity> page = serviceRepository.findAll(spec, Helper.getPageable(request));
        List<ServiceResponse> responses = page.getContent().stream()
                .map(ServiceMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

    private void validateService(ServiceCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (request.getServiceCategoryId() == null) {
            throw new GlobalException("Service Category is mandatory.");
        }
        if (StringUtils.isNotBlank(request.getName()) && request.getServiceClassificationId() != null) {
            if (serviceRepository.existsByNameAndServiceClassificationUid(request.getName(), request.getServiceClassificationId())) {
                throw new GlobalException("A service with this name already exists within the same service classification.");
            }
        }
    }

    private void validateServiceForUpdate(Long id, ServiceCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName()) && request.getServiceClassificationId() != null) {
            if (serviceRepository.existsByNameAndServiceClassificationUidAndUidNot(request.getName(), request.getServiceClassificationId(), id)) {
                throw new GlobalException("A service with this name already exists within the same service classification.");
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

    private void attachValueComponents(ServiceCreateUpdateRequest request, ServiceEntity entity) throws GlobalException {
        if (request.getValueComponentIds() != null && !request.getValueComponentIds().isEmpty()) {
            Set<Long> existingIds = entity.getValueComponents() != null
                    ? entity.getValueComponents().stream().map(ValueComponentEntity::getUid).collect(Collectors.toSet())
                    : Collections.emptySet();

            Set<ValueComponentEntity> valueComponents = request.getValueComponentIds().stream()
                    .map(vcId -> resolveValueComponentForService(vcId, existingIds))
                    .collect(Collectors.toSet());
            entity.setValueComponents(valueComponents);
        }
    }

    private ValueComponentEntity resolveValueComponentForService(Long vcId, Set<Long> existingIds) {
        if (existingIds.contains(vcId)) {
            throw new RuntimeException("The same value component cannot be added to a service more than once.");
        }
        ValueComponentEntity vc = valueComponentRepository.findById(vcId)
                .orElseThrow(() -> new RuntimeException(new GlobalException(ErrorCodeEnum._002.getMessage())));
        if (vc.getStatus() != StatusEnum.ACTIVE) {
            throw new RuntimeException("Only active value components can be assigned.");
        }
        if (vc.getDateFrom() != null && vc.getDateTo() != null && vc.getDateFrom().isAfter(vc.getDateTo())) {
            throw new RuntimeException("Value component has invalid date range.");
        }
        return vc;
    }

    private void attachProducts(ServiceCreateUpdateRequest request, ServiceEntity entity) {
        if (request.getProductIds() != null && !request.getProductIds().isEmpty()) {
            request.getProductIds().forEach(pId -> {
                ProductEntity product = productRepository.findById(pId)
                        .orElseThrow(() -> new RuntimeException(new GlobalException(ErrorCodeEnum._002.getMessage())));
                if (product.getServices() == null) {
                    product.setServices(java.util.Collections.singleton(entity));
                } else {
                    product.getServices().add(entity);
                }
                productRepository.save(product);
            });
        }
    }

}
