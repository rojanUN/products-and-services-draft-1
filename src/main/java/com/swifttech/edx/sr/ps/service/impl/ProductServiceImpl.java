package com.swifttech.edx.sr.ps.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.enums.StatusEnum;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.edx.dm.util.MessageHelper;
import com.swifttech.edx.sr.ps.entity.DynamicProductAttributeEntity;
import com.swifttech.edx.sr.ps.entity.ProductAndServiceClassificationEntity;
import com.swifttech.edx.sr.ps.entity.ProductClassificationEntity;
import com.swifttech.edx.sr.ps.entity.ProductComponentEntity;
import com.swifttech.edx.sr.ps.entity.ProductEntity;
import com.swifttech.edx.sr.ps.entity.ProductTypeEntity;
import com.swifttech.edx.sr.ps.entity.ServiceEntity;
import com.swifttech.edx.sr.ps.entity.ValueComponentEntity;
import com.swifttech.edx.sr.ps.repository.DynamicProductAttributeRepository;
import com.swifttech.edx.sr.ps.repository.ProductAndServiceClassificationRepository;
import com.swifttech.edx.sr.ps.repository.ProductClassificationRepository;
import com.swifttech.edx.sr.ps.repository.ProductComponentRepository;
import com.swifttech.edx.sr.ps.repository.ProductRepository;
import com.swifttech.edx.sr.ps.repository.ProductTypeRepository;
import com.swifttech.edx.sr.ps.repository.ServiceRepository;
import com.swifttech.edx.sr.ps.repository.ValueComponentRepository;
import com.swifttech.edx.sr.ps.mapper.DynamicProductAttributeMapper;
import com.swifttech.edx.sr.ps.mapper.ProductMapper;
import com.swifttech.edx.sr.ps.model.request.ProductCreateUpdateRequest;
import com.swifttech.edx.sr.ps.model.request.ProductDataRequest;
import com.swifttech.edx.sr.ps.model.response.ProductResponse;
import com.swifttech.edx.sr.ps.service.ProductService;
import com.swifttech.edx.sr.ps.service.specification.ProductSpecification;
import com.swifttech.edx.sr.ps.utils.Utility;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductClassificationRepository productClassificationRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductComponentRepository productComponentRepository;
    private final ProductAndServiceClassificationRepository productAndServiceClassificationRepository;
    private final ServiceRepository serviceRepository;
    private final ValueComponentRepository valueComponentRepository;
    private final DynamicProductAttributeRepository dynamicProductAttributeRepository;

    @Override
    @Transactional
    public GlobalResponse createProduct(ProductCreateUpdateRequest request) throws GlobalException {
        validateProduct(request);
        ProductEntity entity = ProductMapper.toEntity(request);
        attachRelations(request, entity);
        attachValueComponents(request, entity);
        attachServices(request, entity);
        ProductEntity saved = Utility.handlePersist(entity, productRepository);
        if (request.getDynamicAttributes() != null && !request.getDynamicAttributes().isEmpty()) {
            saved.getDynamicProductAttributes().clear();
            request.getDynamicAttributes().forEach(attrReq -> {
                DynamicProductAttributeEntity attr = DynamicProductAttributeMapper.toEntity(attrReq);
                attr.setProduct(saved);
                DynamicProductAttributeEntity savedAttr = Utility.handlePersist(attr, dynamicProductAttributeRepository);
                saved.getDynamicProductAttributes().add(savedAttr);
            });
        }
        ProductResponse response = ProductMapper.toResponse(saved);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    @Transactional
    public GlobalResponse updateProduct(Long id, ProductCreateUpdateRequest request) throws GlobalException {
        validateProductForUpdate(id, request);
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ProductMapper.toUpdate(request, entity);
        attachRelations(request, entity);
        attachValueComponents(request, entity);
        attachServices(request, entity);
        if (request.getDynamicAttributes() != null) {
            dynamicProductAttributeRepository.deleteByProductUid(id);
            entity.getDynamicProductAttributes().clear();
            request.getDynamicAttributes().forEach(attrReq -> {
                DynamicProductAttributeEntity attr = DynamicProductAttributeMapper.toEntity(attrReq);
                attr.setProduct(entity);
                DynamicProductAttributeEntity savedAttr = Utility.handlePersist(attr, dynamicProductAttributeRepository);
                entity.getDynamicProductAttributes().add(savedAttr);
            });
        }
        Utility.handlePersist(entity, productRepository);
        return MessageHelper.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findProductById(Long id) throws GlobalException {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ProductResponse response = ProductMapper.toResponse(entity);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findProductPaginatedData(ProductDataRequest request) throws GlobalException {
        Specification<ProductEntity> spec = ProductSpecification.filterBy(request);
        Page<ProductEntity> page = productRepository.findAll(spec, Helper.getPageable(request));
        List<ProductResponse> responses = page.getContent().stream()
                .map(ProductMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return MessageHelper.buildSuccessResponseWithData(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
    }

    private void validateProduct(ProductCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (productRepository.existsByName(request.getName())) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void validateProductForUpdate(Long id, ProductCreateUpdateRequest request) throws GlobalException {
        if (request == null) {
            throw new GlobalException(ErrorCodeEnum._001.getMessage());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            if (productRepository.existsByNameAndUidNot(request.getName(), id)) {
                throw new GlobalException(ErrorCodeEnum._003.getMessage());
            }
        }
    }

    private void attachRelations(ProductCreateUpdateRequest request, ProductEntity entity) throws GlobalException {
        ProductClassificationEntity productClassification = productClassificationRepository.findById(request.getProductClassificationId())
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));

        if (productClassification.getStatus() != StatusEnum.ACTIVE) {
            throw new GlobalException(ErrorCodeEnum._004.getMessage());
        }

        List<ProductClassificationEntity> children = productClassificationRepository
                .findByParentClassificationUid(request.getProductClassificationId());
        if (!children.isEmpty()) {
            throw new GlobalException("Products can only be assigned to a leaf-node classification.");
        }

        entity.setProductClassification(productClassification);

        ProductTypeEntity productType = productTypeRepository.findById(request.getProductTypeId())
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        entity.setProductType(productType);

        if (request.getProductComponentId() != null) {
            ProductComponentEntity productComponent = productComponentRepository.findById(request.getProductComponentId())
                    .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
            entity.setProductComponent(productComponent);
        } else {
            entity.setProductComponent(null);
        }

        ProductAndServiceClassificationEntity psc = productAndServiceClassificationRepository.findById(request.getProductAndServiceClassificationId())
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        entity.setProductAndServiceClassification(psc);
    }

    private void attachValueComponents(ProductCreateUpdateRequest request, ProductEntity entity) throws GlobalException {
        if (request.getValueComponentIds() != null && !request.getValueComponentIds().isEmpty()) {
            Set<ValueComponentEntity> valueComponents = request.getValueComponentIds().stream()
                    .map(vcId -> resolveValueComponentForProduct(vcId))
                    .collect(Collectors.toSet());
            entity.setValueComponents(valueComponents);
        } else {
            entity.setValueComponents(Collections.emptySet());
        }
    }

    private ValueComponentEntity resolveValueComponentForProduct(Long vcId) {
        ValueComponentEntity vc = valueComponentRepository.findById(vcId)
                .orElseThrow(() -> new RuntimeException(new GlobalException(ErrorCodeEnum._002.getMessage())));
        if (vc.getStatus() != StatusEnum.ACTIVE) {
            throw new RuntimeException("Only active value components can be assigned.");
        }
        return vc;
    }

    private void attachServices(ProductCreateUpdateRequest request, ProductEntity entity) throws GlobalException {
        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            ProductClassificationEntity rootProductClassification = findRootClassification(entity.getProductClassification());
            ProductClassificationEntity pscRootClassification = findRootClassification(
                    entity.getProductAndServiceClassification().getProductClassification());

            Set<ServiceEntity> services = request.getServiceIds().stream()
                    .map(sId -> resolveServiceForProduct(sId, rootProductClassification, pscRootClassification))
                    .collect(Collectors.toSet());
            entity.setServices(services);
        } else {
            entity.setServices(Collections.emptySet());
        }
    }

    private ServiceEntity resolveServiceForProduct(Long sId, ProductClassificationEntity rootProductClassification,
                                                    ProductClassificationEntity pscRootClassification) {
        ServiceEntity service = serviceRepository.findById(sId)
                .orElseThrow(() -> new RuntimeException(new GlobalException(ErrorCodeEnum._002.getMessage())));

        if (!rootProductClassification.getUid().equals(pscRootClassification.getUid())) {
            throw new RuntimeException("Product root classification must match the Product and Service Classification's root classification.");
        }

        return service;
    }

    private ProductClassificationEntity findRootClassification(ProductClassificationEntity classification) {
        ProductClassificationEntity current = classification;
        while (current.getParentClassification() != null) {
            current = current.getParentClassification();
        }
        return current;
    }

}
