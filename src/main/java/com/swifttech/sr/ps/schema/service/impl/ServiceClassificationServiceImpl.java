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
import com.swifttech.sr.ps.schema.entity.ServiceClassificationEntity;
import com.swifttech.sr.ps.schema.mapper.ServiceClassificationMapper;
import com.swifttech.sr.ps.schema.model.request.ServiceClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.ServiceClassificationResponse;
import com.swifttech.sr.ps.schema.repository.ServiceClassificationRepository;
import com.swifttech.sr.ps.schema.service.ServiceClassificationService;
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
public class ServiceClassificationServiceImpl implements ServiceClassificationService {

    private final ServiceClassificationRepository serviceClassificationRepository;

    @Override
    public GlobalResponse createServiceClassification(ServiceClassificationCreateUpdateRequest request) throws GlobalException {
        validateServiceClassification(request);
        ServiceClassificationEntity entity = ServiceClassificationMapper.toEntity(request);
        attachParent(request, entity);
        ServiceClassificationEntity saved = Utility.handlePersist(entity, serviceClassificationRepository);
        ServiceClassificationResponse response = ServiceClassificationMapper.toResponse(saved);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateServiceClassification(Long id, ServiceClassificationCreateUpdateRequest request) throws GlobalException {
        validateServiceClassificationForUpdate(id, request);
        ServiceClassificationEntity entity = findEntityById(id);
        ServiceClassificationMapper.toUpdate(request, entity);
        attachParent(request, entity);
        Utility.handlePersist(entity, serviceClassificationRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse updateServiceClassificationStatus(Long id, StatusUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        ServiceClassificationEntity entity = findEntityById(id);
        entity.setStatus(request.getStatus());
        Utility.handlePersist(entity, serviceClassificationRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findServiceClassificationById(Long id) throws GlobalException {
        ServiceClassificationEntity entity = findEntityById(id);
        ServiceClassificationResponse response = ServiceClassificationMapper.toResponse(entity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findServiceClassificationPaginatedData(PaginationRequest request) throws GlobalException {
        Page<ServiceClassificationEntity> page = serviceClassificationRepository.findAll(Helper.getPageable(request));
        List<ServiceClassificationResponse> responses = page.getContent().stream()
                .map(ServiceClassificationMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

    private void validateServiceClassification(ServiceClassificationCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (serviceClassificationRepository.existsByName(request.getName())) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void validateServiceClassificationForUpdate(Long id, ServiceClassificationCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (serviceClassificationRepository.existsByNameAndUidNot(request.getName(), id)) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private ServiceClassificationEntity findEntityById(Long id) throws GlobalException {
        return serviceClassificationRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
    }

    private void attachParent(ServiceClassificationCreateUpdateRequest request, ServiceClassificationEntity entity) throws GlobalException {
        if (request.getParentId() == null) {
            entity.setParentClassification(null);
            return;
        }
        ServiceClassificationEntity parent = serviceClassificationRepository.findById(request.getParentId())
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        entity.setParentClassification(parent);
    }

}
