package com.swifttech.sr.ps.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.builder.ServiceResponseBuilder;
import com.swifttech.edx.dm.entity.BaseEntity;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.request.StatusUpdateRequest;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.entity.ProductClassificationEntity;
import com.swifttech.sr.ps.mapper.ProductClassificationMapper;
import com.swifttech.sr.ps.model.request.ProductClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.model.response.ProductClassificationResponse;
import com.swifttech.sr.ps.repository.ProductClassificationRepository;
import com.swifttech.sr.ps.service.ProductClassificationService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductClassificationServiceImpl implements ProductClassificationService {

    private final ProductClassificationRepository productClassificationRepository;

    @Override
    public GlobalResponse createProductClassification(ProductClassificationCreateUpdateRequest request) throws GlobalException {
        validateProductClassification(request);
        ProductClassificationEntity productClassificationEntity = ProductClassificationMapper.toEntity(request);
        attachParent(request, productClassificationEntity);
        ProductClassificationEntity savedProductClassificationEntity = handlePersist(productClassificationEntity, productClassificationRepository);
        ProductClassificationResponse productClassificationResponse = ProductClassificationMapper.toResponse(savedProductClassificationEntity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), productClassificationResponse);
    }

    @Override
    public GlobalResponse updateProductClassification(Long id, ProductClassificationCreateUpdateRequest request) throws GlobalException {
        return null;
    }

    @Override
    public GlobalResponse updateProductClassificationStatus(Long id, StatusUpdateRequest request) throws GlobalException {
        return null;
    }

    @Override
    public GlobalResponse findProductClassificationById(Long id) throws GlobalException {
        return null;
    }

    @Override
    public GlobalResponse findProductClassificationPaginatedData(PaginationRequest request) throws GlobalException {
        return null;
    }

    //Helpers
    private void validateProductClassification(ProductClassificationCreateUpdateRequest request) throws GlobalException {

        //request null validation
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }

        //same name validatory
        if (StringUtils.isNotBlank(request.getName())) {
            if (productClassificationRepository.existsByName(request.getName())) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    //persistence method
    private <T extends BaseEntity> T handlePersist(T entity, BaseRepository<T> repository) {
        log.info("persisting({}, {})", entity.getClass().getSimpleName(), repository);
        return repository.save(entity);
    }

    //attach parent if available
    private void attachParent(ProductClassificationCreateUpdateRequest request, ProductClassificationEntity entity) throws GlobalException {
        if (request.getParentId() == null) {
            return;
        }
        ProductClassificationEntity parent = productClassificationRepository.findById(request.getParentId()).orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        entity.setParentProductClassification(parent);
    }

}
