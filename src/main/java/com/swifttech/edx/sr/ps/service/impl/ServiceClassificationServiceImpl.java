package com.swifttech.edx.sr.ps.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.util.MessageHelper;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.StatusUpdateRequest;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.edx.sr.ps.entity.ServiceClassificationEntity;
import com.swifttech.edx.sr.ps.mapper.ServiceClassificationMapper;
import com.swifttech.edx.sr.ps.model.request.ServiceClassificationCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.ServiceClassificationDataRequest;
import com.swifttech.edx.sr.ps.model.response.ClassificationHierarchyResponse;
import com.swifttech.edx.sr.ps.model.response.ServiceClassificationResponse;
import com.swifttech.edx.sr.ps.repository.ServiceClassificationRepository;
import com.swifttech.edx.sr.ps.service.ServiceClassificationService;
import com.swifttech.edx.sr.ps.service.specification.ServiceClassificationSpecification;
import com.swifttech.edx.sr.ps.utils.Utility;
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
public class ServiceClassificationServiceImpl implements ServiceClassificationService {

    private final ServiceClassificationRepository serviceClassificationRepository;

    @Override
    public GlobalResponse createServiceClassification(ServiceClassificationCreateUpdateRequest request) throws GlobalException {
        validateServiceClassification(request);
        ServiceClassificationEntity entity = ServiceClassificationMapper.toEntity(request);
        attachParent(request, entity);
        ServiceClassificationEntity saved = Utility.handlePersist(entity, serviceClassificationRepository);
        ServiceClassificationResponse response = ServiceClassificationMapper.toResponse(saved);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse updateServiceClassification(Long id, ServiceClassificationCreateUpdateRequest request) throws GlobalException {
        validateServiceClassificationForUpdate(id, request);
        ServiceClassificationEntity entity = findEntityById(id);
        ServiceClassificationMapper.toUpdate(request, entity);
        attachParent(request, entity);
        Utility.handlePersist(entity, serviceClassificationRepository);
        return MessageHelper.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse updateServiceClassificationStatus(Long id, StatusUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        ServiceClassificationEntity entity = findEntityById(id);
        entity.setStatus(request.getStatus());
        Utility.handlePersist(entity, serviceClassificationRepository);
        return MessageHelper.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findServiceClassificationById(Long id) throws GlobalException {
        ServiceClassificationEntity entity = findEntityById(id);
        ServiceClassificationResponse response = ServiceClassificationMapper.toResponse(entity);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findServiceClassificationPaginatedData(ServiceClassificationDataRequest request) throws GlobalException {
        Specification<ServiceClassificationEntity> spec = ServiceClassificationSpecification.filterBy(request);
        Page<ServiceClassificationEntity> page = serviceClassificationRepository.findAll(spec, Helper.getPageable(request));
        List<ServiceClassificationResponse> responses = page.getContent().stream()
                .map(ServiceClassificationMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

    private void validateServiceClassification(ServiceClassificationCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (serviceClassificationRepository.existsByName(request.getName())) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void validateServiceClassificationForUpdate(Long id, ServiceClassificationCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (serviceClassificationRepository.existsByNameAndUidNot(request.getName(), id)) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private ServiceClassificationEntity findEntityById(Long id) throws GlobalException {
        return serviceClassificationRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
    }

    @Override
    public GlobalResponse findServiceClassificationHierarchy() throws GlobalException {
        List<ServiceClassificationEntity> roots = serviceClassificationRepository.findByParentClassificationIsNull();
        List<ClassificationHierarchyResponse> tree = roots.stream()
                .map(root -> buildHierarchyTree(root, new HashSet<>()))
                .toList();
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), tree);
    }

    private ClassificationHierarchyResponse buildHierarchyTree(ServiceClassificationEntity entity, Set<Long> visited) {
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
        List<ServiceClassificationEntity> children = serviceClassificationRepository
                .findByParentClassificationUid(entity.getUid());
        return ClassificationHierarchyResponse.builder()
                .id(entity.getUid())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .children(children.stream().map(child -> buildHierarchyTree(child, visited)).toList())
                .build();
    }

    private void attachParent(ServiceClassificationCreateUpdateRequest request, ServiceClassificationEntity entity) throws GlobalException {
        if (request.getParentId() == null) {
            entity.setParentClassification(null);
            return;
        }
        if (entity.getUid() != null && entity.getUid().equals(request.getParentId())) {
            throw new GlobalException("A classification cannot be its own parent.");
        }
        ServiceClassificationEntity parent = serviceClassificationRepository.findById(request.getParentId())
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        if (parent.getStatus() != com.swifttech.edx.dm.enums.StatusEnum.ACTIVE) {
            throw new GlobalException("A classification cannot be assigned to an inactive parent.");
        }
        entity.setParentClassification(parent);
    }

}
