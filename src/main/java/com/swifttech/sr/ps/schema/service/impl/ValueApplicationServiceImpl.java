package com.swifttech.sr.ps.schema.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.builder.ServiceResponseBuilder;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.schema.entity.ValueApplicationEntity;
import com.swifttech.sr.ps.schema.mapper.ValueApplicationMapper;
import com.swifttech.sr.ps.schema.model.request.ValueApplicationCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.ValueApplicationResponse;
import com.swifttech.sr.ps.schema.repository.ValueApplicationRepository;
import com.swifttech.sr.ps.schema.service.ValueApplicationService;
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
public class ValueApplicationServiceImpl implements ValueApplicationService {

    private final ValueApplicationRepository repository;

    @Override
    public GlobalResponse createValueApplication(ValueApplicationCreateUpdateRequest request) throws GlobalException {
        if (request == null) throw new GlobalException(ErrorCodeEnum._001.getMessage());
        if (StringUtils.isNotBlank(request.getName()) && repository.existsByName(request.getName())) {
            throw new GlobalException(ErrorCodeEnum._003.getMessage());
        }
        ValueApplicationEntity entity = ValueApplicationMapper.toEntity(request);
        ValueApplicationEntity saved = Utility.handlePersist(entity, repository);
        ValueApplicationResponse response = ValueApplicationMapper.toResponse(saved);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateValueApplication(Long id, ValueApplicationCreateUpdateRequest request) throws GlobalException {
        if (request == null) throw new GlobalException(ErrorCodeEnum._001.getMessage());
        if (StringUtils.isNotBlank(request.getName()) && repository.existsByNameAndUidNot(request.getName(), id)) {
            throw new GlobalException(ErrorCodeEnum._003.getMessage());
        }
        ValueApplicationEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ValueApplicationMapper.toUpdate(request, entity);
        Utility.handlePersist(entity, repository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findValueApplicationById(Long id) throws GlobalException {
        ValueApplicationEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ValueApplicationResponse response = ValueApplicationMapper.toResponse(entity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findValueApplicationPaginatedData(PaginationRequest request) throws GlobalException {
        Page<ValueApplicationEntity> page = repository.findAll(Helper.getPageable(request));
        List<ValueApplicationResponse> responses = page.getContent().stream()
                .map(ValueApplicationMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

}
