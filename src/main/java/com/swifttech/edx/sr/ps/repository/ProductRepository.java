package com.swifttech.edx.sr.ps.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.edx.sr.ps.entity.ProductEntity;

import java.util.List;

public interface ProductRepository extends BaseRepository<ProductEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);
    boolean existsByNameAndProductClassificationUid(String name, Long productClassificationId);
    boolean existsByNameAndProductClassificationUidAndUidNot(String name, Long productClassificationId, Long uid);

    List<ProductEntity> findByProductClassificationUid(Long productClassificationId);

    List<ProductEntity> findByProductTypeUid(Long productTypeId);

    List<ProductEntity> findByServicesUid(Long serviceId);

}
