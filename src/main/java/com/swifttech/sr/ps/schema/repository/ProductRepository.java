package com.swifttech.sr.ps.schema.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.schema.entity.ProductEntity;

import java.util.List;

public interface ProductRepository extends BaseRepository<ProductEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);

    List<ProductEntity> findByProductClassificationUid(Long productClassificationId);

    List<ProductEntity> findByProductTypeUid(Long productTypeId);

    List<ProductEntity> findByServiceUid(Long serviceId);

}
