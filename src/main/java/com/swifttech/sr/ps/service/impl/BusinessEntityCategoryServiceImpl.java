package com.swifttech.sr.ps.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.util.MessageHelper;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.entity.BusinessEntityCategoryEntity;
import com.swifttech.sr.ps.mapper.BusinessEntityCategoryMapper;
import com.swifttech.sr.ps.model.request.BusinessEntityCategoryCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.BusinessEntityCategoryDataRequest;
import com.swifttech.sr.ps.model.response.BusinessEntityCategoryResponse;
import com.swifttech.sr.ps.repository.BusinessEntityCategoryRepository;
import com.swifttech.sr.ps.service.BusinessEntityCategoryService;
import com.swifttech.sr.ps.service.specification.BusinessEntityCategorySpecification;
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
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
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
        return MessageHelper.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findBusinessEntityCategoryById(Long id) throws GlobalException {
        BusinessEntityCategoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        BusinessEntityCategoryResponse response = BusinessEntityCategoryMapper.toResponse(entity);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findBusinessEntityCategoryPaginatedData(BusinessEntityCategoryDataRequest request) throws GlobalException {
        Specification<BusinessEntityCategoryEntity> spec = BusinessEntityCategorySpecification.filterBy(request);
        Page<BusinessEntityCategoryEntity> page = repository.findAll(spec, Helper.getPageable(request));
        List<BusinessEntityCategoryResponse> responses = page.getContent().stream()
                .map(BusinessEntityCategoryMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

}
