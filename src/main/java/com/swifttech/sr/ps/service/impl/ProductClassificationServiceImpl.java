package com.swifttech.sr.ps.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.util.MessageHelper;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.StatusUpdateRequest;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.entity.ProductClassificationEntity;
import com.swifttech.sr.ps.mapper.ProductClassificationMapper;
import com.swifttech.sr.ps.model.request.ProductClassificationCreateUpdateRequest;
import com.swifttech.sr.ps.model.request.ProductClassificationDataRequest;
import com.swifttech.sr.ps.model.response.ClassificationHierarchyResponse;
import com.swifttech.sr.ps.model.response.ProductClassificationResponse;
import com.swifttech.sr.ps.repository.ProductClassificationRepository;
import com.swifttech.sr.ps.service.ProductClassificationService;
import com.swifttech.sr.ps.service.specification.ProductClassificationSpecification;
import com.swifttech.sr.ps.utils.Utility;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductClassificationServiceImpl implements ProductClassificationService {

    private final ProductClassificationRepository productClassificationRepository;

    @Override
    public GlobalResponse createProductClassification(ProductClassificationCreateUpdateRequest request) throws GlobalException {
        validateProductClassification(request);
        ProductClassificationEntity entity = ProductClassificationMapper.toEntity(request);
        attachParent(request, entity);
        ProductClassificationEntity saved = Utility.handlePersist(entity, productClassificationRepository);
        ProductClassificationResponse response = ProductClassificationMapper.toResponse(saved);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateProductClassification(Long id, ProductClassificationCreateUpdateRequest request) throws GlobalException {
        validateProductClassificationForUpdate(id, request);
        ProductClassificationEntity entity = findEntityById(id);
        ProductClassificationMapper.toUpdate(request, entity);
        attachParent(request, entity);
        Utility.handlePersist(entity, productClassificationRepository);
        return MessageHelper.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse updateProductClassificationStatus(Long id, StatusUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        ProductClassificationEntity entity = findEntityById(id);
        entity.setStatus(request.getStatus());
        Utility.handlePersist(entity, productClassificationRepository);
        return MessageHelper.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findProductClassificationById(Long id) throws GlobalException {
        ProductClassificationEntity entity = findEntityById(id);
        ProductClassificationResponse response = ProductClassificationMapper.toResponse(entity);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findProductClassificationPaginatedData(ProductClassificationDataRequest request) throws GlobalException {
        Specification<ProductClassificationEntity> spec = ProductClassificationSpecification.filterBy(request);
        Page<ProductClassificationEntity> page = productClassificationRepository.findAll(spec, Helper.getPageable(request));
        List<ProductClassificationResponse> responses = page.getContent().stream()
                .map(ProductClassificationMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

    private void validateProductClassification(ProductClassificationCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (productClassificationRepository.existsByName(request.getName())) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void validateProductClassificationForUpdate(Long id, ProductClassificationCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (productClassificationRepository.existsByNameAndUidNot(request.getName(), id)) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private ProductClassificationEntity findEntityById(Long id) throws GlobalException {
        return productClassificationRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
    }

    @Override
    public GlobalResponse findProductClassificationHierarchy() throws GlobalException {
        List<ProductClassificationEntity> roots = productClassificationRepository.findByParentClassificationIsNull();
        List<ClassificationHierarchyResponse> tree = roots.stream()
                .map(root -> buildHierarchyTree(root, new HashSet<>()))
                .toList();
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), tree);
    }

    private ClassificationHierarchyResponse buildHierarchyTree(ProductClassificationEntity entity, Set<Long> visited) {
        if (visited.contains(entity.getUid())) {
            return ClassificationHierarchyResponse.builder()
                    .id(entity.getUid())
                    .name(entity.getName() + " (cycle detected)")
                    .description(entity.getDescription())
                    .status(entity.getStatus())
                    .children(List.of())
                    .build();
        }
        visited.add(entity.getUid());
        List<ProductClassificationEntity> children = productClassificationRepository
                .findByParentClassificationUid(entity.getUid());
        return ClassificationHierarchyResponse.builder()
                .id(entity.getUid())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .children(children.stream().map(child -> buildHierarchyTree(child, visited)).toList())
                .build();
    }

    private void attachParent(ProductClassificationCreateUpdateRequest request, ProductClassificationEntity entity) throws GlobalException {
        if (request.getParentId() == null) {
            entity.setParentClassification(null);
            return;
        }
        if (entity.getUid() != null && entity.getUid().equals(request.getParentId())) {
            throw new GlobalException("A classification cannot be its own parent.");
        }
        ProductClassificationEntity parent = productClassificationRepository.findById(request.getParentId())
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        if (parent.getStatus() != com.swifttech.edx.dm.enums.StatusEnum.ACTIVE) {
            throw new GlobalException("A classification cannot be assigned to an inactive parent.");
        }
        entity.setParentClassification(parent);
    }

}
