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
import com.swifttech.sr.ps.entity.ServiceClassificationEntity;
import com.swifttech.sr.ps.mapper.ServiceClassificationMapper;
import com.swifttech.sr.ps.model.request.ServiceClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.model.response.ServiceClassificationResponse;
import com.swifttech.sr.ps.repository.ServiceClassificationRepository;
import com.swifttech.sr.ps.service.ServiceClassificationService;
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
        ServiceClassificationEntity serviceClassificationEntity = ServiceClassificationMapper.toEntity(request);
        attachParent(request, serviceClassificationEntity);
        ServiceClassificationEntity savedServiceClassificationEntity = Utility.handlePersist(serviceClassificationEntity, serviceClassificationRepository);
        ServiceClassificationResponse serviceClassificationResponse = ServiceClassificationMapper.toResponse(savedServiceClassificationEntity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), serviceClassificationResponse);
    }

    @Override
    public GlobalResponse updateServiceClassification(Long id, ServiceClassificationCreateUpdateRequest request) throws GlobalException {
        validateServiceClassificationForUpdate(id, request);
        ServiceClassificationEntity serviceClassificationEntity = findServiceClassificationEntityById(id);
        ServiceClassificationMapper.toUpdate(request, serviceClassificationEntity);
        attachParent(request, serviceClassificationEntity);
        Utility.handlePersist(serviceClassificationEntity, serviceClassificationRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse updateServiceClassificationStatus(Long id, StatusUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        ServiceClassificationEntity serviceClassificationEntity = findServiceClassificationEntityById(id);
        serviceClassificationEntity.setStatus(request.getStatus());
        Utility.handlePersist(serviceClassificationEntity, serviceClassificationRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findServiceClassificationById(Long id) throws GlobalException {
        ServiceClassificationEntity serviceClassificationEntity = findServiceClassificationEntityById(id);
        ServiceClassificationResponse serviceClassificationResponse = ServiceClassificationMapper.toResponse(serviceClassificationEntity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), serviceClassificationResponse);
    }

    @Override
    public GlobalResponse findServiceClassificationPaginatedData(PaginationRequest request) throws GlobalException {
        Page<ServiceClassificationEntity> serviceClassificationPage = serviceClassificationRepository.findAll(Helper.getPageable(request));
        List<ServiceClassificationResponse> serviceClassificationResponses = serviceClassificationPage.getContent().stream()
                .map(ServiceClassificationMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(serviceClassificationPage.getTotalElements(), serviceClassificationResponses);
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

    private ServiceClassificationEntity findServiceClassificationEntityById(Long id) throws GlobalException {
        return serviceClassificationRepository.findById(id).orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
    }

    private void attachParent(ServiceClassificationCreateUpdateRequest request, ServiceClassificationEntity entity) throws GlobalException {
        if (request.getParentId() == null) {
            entity.setParentServiceClassification(null);
            return;
        }
        ServiceClassificationEntity parent = serviceClassificationRepository.findById(request.getParentId()).orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        entity.setParentServiceClassification(parent);
    }

}
