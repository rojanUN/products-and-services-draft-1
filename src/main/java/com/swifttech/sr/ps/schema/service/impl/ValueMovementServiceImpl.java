package com.swifttech.sr.ps.schema.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.builder.ServiceResponseBuilder;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.schema.entity.ValueMovementEntity;
import com.swifttech.sr.ps.schema.mapper.ValueMovementMapper;
import com.swifttech.sr.ps.schema.model.request.ValueMovementCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.ValueMovementResponse;
import com.swifttech.sr.ps.schema.repository.ValueMovementRepository;
import com.swifttech.sr.ps.schema.service.ValueMovementService;
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
public class ValueMovementServiceImpl implements ValueMovementService {

    private final ValueMovementRepository repository;

    @Override
    public GlobalResponse createValueMovement(ValueMovementCreateUpdateRequest request) throws GlobalException {
        if (request == null) throw new GlobalException(ErrorCodeEnum._001.getMessage());
        if (StringUtils.isNotBlank(request.getName()) && repository.existsByName(request.getName())) {
            throw new GlobalException(ErrorCodeEnum._003.getMessage());
        }
        ValueMovementEntity entity = ValueMovementMapper.toEntity(request);
        ValueMovementEntity saved = Utility.handlePersist(entity, repository);
        ValueMovementResponse response = ValueMovementMapper.toResponse(saved);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateValueMovement(Long id, ValueMovementCreateUpdateRequest request) throws GlobalException {
        if (request == null) throw new GlobalException(ErrorCodeEnum._001.getMessage());
        if (StringUtils.isNotBlank(request.getName()) && repository.existsByNameAndUidNot(request.getName(), id)) {
            throw new GlobalException(ErrorCodeEnum._003.getMessage());
        }
        ValueMovementEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ValueMovementMapper.toUpdate(request, entity);
        Utility.handlePersist(entity, repository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findValueMovementById(Long id) throws GlobalException {
        ValueMovementEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ValueMovementResponse response = ValueMovementMapper.toResponse(entity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findValueMovementPaginatedData(PaginationRequest request) throws GlobalException {
        Page<ValueMovementEntity> page = repository.findAll(Helper.getPageable(request));
        List<ValueMovementResponse> responses = page.getContent().stream()
                .map(ValueMovementMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

}
