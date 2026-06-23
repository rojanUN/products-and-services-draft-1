package com.swifttech.sr.ps.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.util.MessageHelper;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.entity.ValueComponentTypeEntity;
import com.swifttech.sr.ps.mapper.ValueComponentTypeMapper;
import com.swifttech.sr.ps.model.request.ValueComponentTypeCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ValueComponentTypeDataRequest;
import com.swifttech.sr.ps.model.response.ValueComponentTypeResponse;
import com.swifttech.sr.ps.repository.ValueComponentTypeRepository;
import com.swifttech.sr.ps.service.ValueComponentTypeService;
import com.swifttech.sr.ps.service.specification.ValueComponentTypeSpecification;
import com.swifttech.sr.ps.utils.Utility;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValueComponentTypeServiceImpl implements ValueComponentTypeService {

    private final ValueComponentTypeRepository repository;

    @Override
    public GlobalResponse createValueComponentType(ValueComponentTypeCreateUpdateRequest request) throws GlobalException {
        if (request == null) throw new GlobalException(ErrorCodeEnum._001.getMessage());
        if (StringUtils.isNotBlank(request.getName()) && repository.existsByName(request.getName())) {
            throw new GlobalException(ErrorCodeEnum._003.getMessage());
        }
        ValueComponentTypeEntity entity = ValueComponentTypeMapper.toEntity(request);
        ValueComponentTypeEntity saved = Utility.handlePersist(entity, repository);
        ValueComponentTypeResponse response = ValueComponentTypeMapper.toResponse(saved);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateValueComponentType(Long id, ValueComponentTypeCreateUpdateRequest request) throws GlobalException {
        if (request == null) throw new GlobalException(ErrorCodeEnum._001.getMessage());
        if (StringUtils.isNotBlank(request.getName()) && repository.existsByNameAndUidNot(request.getName(), id)) {
            throw new GlobalException(ErrorCodeEnum._003.getMessage());
        }
        ValueComponentTypeEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ValueComponentTypeMapper.toUpdate(request, entity);
        Utility.handlePersist(entity, repository);
        return MessageHelper.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findValueComponentTypeById(Long id) throws GlobalException {
        ValueComponentTypeEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ValueComponentTypeResponse response = ValueComponentTypeMapper.toResponse(entity);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findValueComponentTypePaginatedData(ValueComponentTypeDataRequest request) throws GlobalException {
        Specification<ValueComponentTypeEntity> spec = ValueComponentTypeSpecification.filterBy(request);
        Page<ValueComponentTypeEntity> page = repository.findAll(spec, Helper.getPageable(request));
        List<ValueComponentTypeResponse> responses = page.getContent().stream()
                .map(ValueComponentTypeMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

}
