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
import com.swifttech.sr.ps.schema.entity.ValueComponentEntity;
import com.swifttech.sr.ps.schema.entity.ValueMovementEntity;
import com.swifttech.sr.ps.schema.entity.ValueApplicationEntity;
import com.swifttech.sr.ps.schema.entity.ValueComponentTypeEntity;
import com.swifttech.sr.ps.schema.mapper.ValueComponentMapper;
import com.swifttech.sr.ps.schema.model.request.ValueComponentCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.ValueComponentResponse;
import com.swifttech.sr.ps.schema.repository.*;
import com.swifttech.sr.ps.schema.service.ValueComponentService;
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
public class ValueComponentServiceImpl implements ValueComponentService {

    private final ValueComponentRepository valueComponentRepository;
    private final ValueMovementRepository valueMovementRepository;
    private final ValueApplicationRepository valueApplicationRepository;
    private final ValueComponentTypeRepository valueComponentTypeRepository;

    @Override
    public GlobalResponse createValueComponent(ValueComponentCreateUpdateRequest request) throws GlobalException {
        validateValueComponent(request);
        ValueComponentEntity entity = ValueComponentMapper.toEntity(request);
        attachRelations(request, entity);
        ValueComponentEntity saved = Utility.handlePersist(entity, valueComponentRepository);
        ValueComponentResponse response = ValueComponentMapper.toResponse(saved);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateValueComponent(Long id, ValueComponentCreateUpdateRequest request) throws GlobalException {
        validateValueComponentForUpdate(id, request);
        ValueComponentEntity entity = valueComponentRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ValueComponentMapper.toUpdate(request, entity);
        attachRelations(request, entity);
        Utility.handlePersist(entity, valueComponentRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse updateValueComponentStatus(Long id, StatusUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        ValueComponentEntity entity = valueComponentRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        entity.setStatus(request.getStatus());
        Utility.handlePersist(entity, valueComponentRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findValueComponentById(Long id) throws GlobalException {
        ValueComponentEntity entity = valueComponentRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ValueComponentResponse response = ValueComponentMapper.toResponse(entity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findValueComponentPaginatedData(PaginationRequest request) throws GlobalException {
        Page<ValueComponentEntity> page = valueComponentRepository.findAll(Helper.getPageable(request));
        List<ValueComponentResponse> responses = page.getContent().stream()
                .map(ValueComponentMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

    private void validateValueComponent(ValueComponentCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (valueComponentRepository.existsByName(request.getName())) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void validateValueComponentForUpdate(Long id, ValueComponentCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (valueComponentRepository.existsByNameAndUidNot(request.getName(), id)) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void attachRelations(ValueComponentCreateUpdateRequest request, ValueComponentEntity entity) throws GlobalException {
        if (request.getBasedOnId() != null) {
            ValueComponentEntity basedOn = valueComponentRepository.findById(request.getBasedOnId())
                    .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
            entity.setBasedOn(basedOn);
        } else {
            entity.setBasedOn(null);
        }

        if (request.getValueMovementId() != null) {
            ValueMovementEntity valueMovement = valueMovementRepository.findById(request.getValueMovementId())
                    .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
            entity.setValueMovement(valueMovement);
        } else {
            entity.setValueMovement(null);
        }

        if (request.getAppliesToId() != null) {
            ValueApplicationEntity appliesTo = valueApplicationRepository.findById(request.getAppliesToId())
                    .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
            entity.setAppliesTo(appliesTo);
        } else {
            entity.setAppliesTo(null);
        }

        if (request.getComponentTypeId() != null) {
            ValueComponentTypeEntity componentType = valueComponentTypeRepository.findById(request.getComponentTypeId())
                    .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
            entity.setComponentType(componentType);
        } else {
            entity.setComponentType(null);
        }
    }

}
