package com.swifttech.sr.ps.schema.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.builder.ServiceResponseBuilder;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.schema.entity.CurrencyClassificationEntity;
import com.swifttech.sr.ps.schema.mapper.CurrencyClassificationMapper;
import com.swifttech.sr.ps.schema.model.request.CurrencyClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.CurrencyClassificationResponse;
import com.swifttech.sr.ps.schema.repository.CurrencyClassificationRepository;
import com.swifttech.sr.ps.schema.service.CurrencyClassificationService;
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
public class CurrencyClassificationServiceImpl implements CurrencyClassificationService {

    private final CurrencyClassificationRepository repository;

    @Override
    public GlobalResponse createCurrencyClassification(CurrencyClassificationCreateUpdateRequest request) throws GlobalException {
        if (request == null) throw new GlobalException(ErrorCodeEnum._001.getMessage());
        if (StringUtils.isNotBlank(request.getName()) && repository.existsByName(request.getName())) {
            throw new GlobalException(ErrorCodeEnum._003.getMessage());
        }
        CurrencyClassificationEntity entity = CurrencyClassificationMapper.toEntity(request);
        CurrencyClassificationEntity saved = Utility.handlePersist(entity, repository);
        CurrencyClassificationResponse response = CurrencyClassificationMapper.toResponse(saved);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateCurrencyClassification(Long id, CurrencyClassificationCreateUpdateRequest request) throws GlobalException {
        if (request == null) throw new GlobalException(ErrorCodeEnum._001.getMessage());
        if (StringUtils.isNotBlank(request.getName()) && repository.existsByNameAndUidNot(request.getName(), id)) {
            throw new GlobalException(ErrorCodeEnum._003.getMessage());
        }
        CurrencyClassificationEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        CurrencyClassificationMapper.toUpdate(request, entity);
        Utility.handlePersist(entity, repository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findCurrencyClassificationById(Long id) throws GlobalException {
        CurrencyClassificationEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        CurrencyClassificationResponse response = CurrencyClassificationMapper.toResponse(entity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findCurrencyClassificationPaginatedData(PaginationRequest request) throws GlobalException {
        Page<CurrencyClassificationEntity> page = repository.findAll(Helper.getPageable(request));
        List<CurrencyClassificationResponse> responses = page.getContent().stream()
                .map(CurrencyClassificationMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

}
