package com.swifttech.sr.ps.schema.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.builder.ServiceResponseBuilder;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.schema.entity.BusinessEntityCategoryEntity;
import com.swifttech.sr.ps.schema.mapper.BusinessEntityCategoryMapper;
import com.swifttech.sr.ps.schema.model.request.BusinessEntityCategoryCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.BusinessEntityCategoryResponse;
import com.swifttech.sr.ps.schema.repository.BusinessEntityCategoryRepository;
import com.swifttech.sr.ps.schema.service.BusinessEntityCategoryService;
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
public class BusinessEntityCategoryServiceImpl implements BusinessEntityCategoryService {

    private final BusinessEntityCategoryRepository repository;

    @Override
    public GlobalResponse createBusinessEntityCategory(BusinessEntityCategoryCreateUpdateRequest request) throws GlobalException {
        if (request == null) throw new GlobalException(ErrorCodeEnum._001.getMessage());
        if (StringUtils.isNotBlank(request.getName()) && repository.existsByName(request.getName())) {
            throw new GlobalException(ErrorCodeEnum._003.getMessage());
        }
        BusinessEntityCategoryEntity entity = BusinessEntityCategoryMapper.toEntity(request);
        BusinessEntityCategoryEntity saved = Utility.handlePersist(entity, repository);
        BusinessEntityCategoryResponse response = BusinessEntityCategoryMapper.toResponse(saved);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateBusinessEntityCategory(Long id, BusinessEntityCategoryCreateUpdateRequest request) throws GlobalException {
        if (request == null) throw new GlobalException(ErrorCodeEnum._001.getMessage());
        if (StringUtils.isNotBlank(request.getName()) && repository.existsByNameAndUidNot(request.getName(), id)) {
            throw new GlobalException(ErrorCodeEnum._003.getMessage());
        }
        BusinessEntityCategoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        BusinessEntityCategoryMapper.toUpdate(request, entity);
        Utility.handlePersist(entity, repository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findBusinessEntityCategoryById(Long id) throws GlobalException {
        BusinessEntityCategoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        BusinessEntityCategoryResponse response = BusinessEntityCategoryMapper.toResponse(entity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findBusinessEntityCategoryPaginatedData(PaginationRequest request) throws GlobalException {
        Page<BusinessEntityCategoryEntity> page = repository.findAll(Helper.getPageable(request));
        List<BusinessEntityCategoryResponse> responses = page.getContent().stream()
                .map(BusinessEntityCategoryMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

}
