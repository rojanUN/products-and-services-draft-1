package com.swifttech.sr.ps.schema.service.impl;

import com.swifttech.edx.dm.am.enums.ErrorCodeEnum;
import com.swifttech.edx.dm.am.enums.SuccessCodeEnum;
import com.swifttech.edx.dm.builder.ServiceResponseBuilder;
import com.swifttech.edx.dm.exception.GlobalException;
import com.swifttech.edx.dm.payload.request.PaginationRequest;
import com.swifttech.edx.dm.payload.response.DataPaginationResponse;
import com.swifttech.edx.dm.payload.response.GlobalResponse;
import com.swifttech.edx.dm.util.Helper;
import com.swifttech.sr.ps.schema.entity.*;
import com.swifttech.sr.ps.schema.mapper.DynamicProductAttributeMapper;
import com.swifttech.sr.ps.schema.mapper.ProductMapper;
import com.swifttech.sr.ps.schema.model.request.ProductCreateUpdateRequest;
import com.swifttech.sr.ps.schema.model.response.ProductResponse;
import com.swifttech.sr.ps.schema.repository.*;
import com.swifttech.sr.ps.schema.service.ProductService;
import com.swifttech.sr.ps.utils.Utility;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
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
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage());
    }

    @Override
    public GlobalResponse findProductById(Long id) throws GlobalException {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
        ProductResponse response = ProductMapper.toResponse(entity);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), response);
    }

    @Override
    public GlobalResponse findProductPaginatedData(PaginationRequest request) throws GlobalException {
        Page<ProductEntity> page = productRepository.findAll(Helper.getPageable(request));
        List<ProductResponse> responses = page.getContent().stream()
                .map(ProductMapper::toResponse)
                .toList();
        DataPaginationResponse dataPaginationResponse = new DataPaginationResponse(page.getTotalElements(), responses);
        return ServiceResponseBuilder.buildSuccessResponse(SuccessCodeEnum._100.getMessage(), dataPaginationResponse);
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

        if (request.getServiceId() != null) {
            ServiceEntity service = serviceRepository.findById(request.getServiceId())
                    .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
            entity.setService(service);
        } else {
            entity.setService(null);
        }
    }

    private void attachValueComponents(ProductCreateUpdateRequest request, ProductEntity entity) {
        if (request.getValueComponentIds() != null && !request.getValueComponentIds().isEmpty()) {
            Set<ValueComponentEntity> valueComponents = request.getValueComponentIds().stream()
                    .map(vcId -> {
                        try {
                            return valueComponentRepository.findById(vcId)
                                    .orElseThrow(() -> new GlobalException(ErrorCodeEnum._002.getMessage()));
                        } catch (GlobalException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toSet());
            entity.setValueComponents(valueComponents);
        } else {
            entity.setValueComponents(Collections.emptySet());
        }
    }

}
