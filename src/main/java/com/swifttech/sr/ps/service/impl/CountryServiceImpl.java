package com.swifttech.sr.ps.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.util.MessageHelper;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.entity.CountryEntity;
import com.swifttech.sr.ps.mapper.CountryMapper;
import com.swifttech.sr.ps.model.request.CountryCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.CountryDataRequest;
import com.swifttech.sr.ps.model.response.CountryResponse;
import com.swifttech.sr.ps.repository.CountryRepository;
import com.swifttech.sr.ps.service.CountryService;
import com.swifttech.sr.ps.service.specification.CountrySpecification;
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
public class CountryServiceImpl implements CountryService {

    private final CountryRepository repository;

    @Override
    public GlobalResponse createCountry(CountryCreateUpdateRequest request) throws GlobalException {
        if (request == null) throw new GlobalException(ErrorCodeEnum._001.getMessage());
        if (StringUtils.isNotBlank(request.getName()) && repository.existsByName(request.getName())) {
            throw new GlobalException(ErrorCodeEnum._003.getMessage());
        }
        CountryEntity entity = CountryMapper.toEntity(request);
        CountryEntity saved = Utility.handlePersist(entity, repository);
        CountryResponse response = CountryMapper.toResponse(saved);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateCountry(Long id, CountryCreateUpdateRequest request) throws GlobalException {
        if (request == null) throw new GlobalException(ErrorCodeEnum._001.getMessage());
        if (StringUtils.isNotBlank(request.getName()) && repository.existsByNameAndUidNot(request.getName(), id)) {
            throw new GlobalException(ErrorCodeEnum._003.getMessage());
        }
        CountryEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        CountryMapper.toUpdate(request, entity);
        Utility.handlePersist(entity, repository);
        return MessageHelper.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findCountryById(Long id) throws GlobalException {
        CountryEntity entity = repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        CountryResponse response = CountryMapper.toResponse(entity);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findCountryPaginatedData(CountryDataRequest request) throws GlobalException {
        Specification<CountryEntity> spec = CountrySpecification.filterBy(request);
        Page<CountryEntity> page = repository.findAll(spec, Helper.getPageable(request));
        List<CountryResponse> responses = page.getContent().stream()
                .map(CountryMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

}
