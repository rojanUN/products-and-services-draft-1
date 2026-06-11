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
import com.swifttech.sr.ps.schema.entity.CurrencyEntity;
import com.swifttech.sr.ps.schema.entity.CountryEntity;
import com.swifttech.sr.ps.schema.entity.CurrencyClassificationEntity;
import com.swifttech.sr.ps.schema.mapper.CurrencyMapper;
import com.swifttech.sr.ps.schema.model.request.CurrencyCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.CurrencyResponse;
import com.swifttech.sr.ps.schema.repository.CurrencyRepository;
import com.swifttech.sr.ps.schema.repository.CountryRepository;
import com.swifttech.sr.ps.schema.repository.CurrencyClassificationRepository;
import com.swifttech.sr.ps.schema.service.CurrencyService;
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
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CountryRepository countryRepository;
    private final CurrencyClassificationRepository currencyClassificationRepository;

    @Override
    public GlobalResponse createCurrency(CurrencyCreateUpdateRequest request) throws GlobalException {
        validateCurrency(request);
        CurrencyEntity entity = CurrencyMapper.toEntity(request);
        attachRelations(request, entity);
        CurrencyEntity saved = Utility.handlePersist(entity, currencyRepository);
        CurrencyResponse response = CurrencyMapper.toResponse(saved);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateCurrency(Long id, CurrencyCreateUpdateRequest request) throws GlobalException {
        validateCurrencyForUpdate(id, request);
        CurrencyEntity entity = currencyRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        CurrencyMapper.toUpdate(request, entity);
        attachRelations(request, entity);
        Utility.handlePersist(entity, currencyRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse updateCurrencyStatus(Long id, StatusUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        CurrencyEntity entity = currencyRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        entity.setStatus(request.getStatus());
        Utility.handlePersist(entity, currencyRepository);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findCurrencyById(Long id) throws GlobalException {
        CurrencyEntity entity = currencyRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        CurrencyResponse response = CurrencyMapper.toResponse(entity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findCurrencyPaginatedData(PaginationRequest request) throws GlobalException {
        Page<CurrencyEntity> page = currencyRepository.findAll(Helper.getPageable(request));
        List<CurrencyResponse> responses = page.getContent().stream()
                .map(CurrencyMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

    private void validateCurrency(CurrencyCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (currencyRepository.existsByName(request.getName())) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
        if (StringUtils.isNotBlank(request.getAlphaThree())) {
            if (currencyRepository.existsByAlphaThree(request.getAlphaThree())) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void validateCurrencyForUpdate(Long id, CurrencyCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (currencyRepository.existsByNameAndUidNot(request.getName(), id)) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void attachRelations(CurrencyCreateUpdateRequest request, CurrencyEntity entity) throws GlobalException {
       /* if (request.getCountryId() != null) {
            CountryEntity country = countryRepository.findById(request.getCountryId())
                    .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
            entity.setCountry(country);
        } else {
            entity.setCountry(null);
        }*/

   /*     if (request.getClassificationId() != null) {
            CurrencyClassificationEntity classification = currencyClassificationRepository.findById(request.getClassificationId())
                    .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
            entity.setClassification(classification);
        } else {
            entity.setClassification(null);
        }*/
    }

}
