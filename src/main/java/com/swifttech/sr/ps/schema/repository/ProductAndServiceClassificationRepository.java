package com.swifttech.sr.ps.schema.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.schema.entity.ProductAndServiceClassificationEntity;

import java.util.List;

public interface ProductAndServiceClassificationRepository extends BaseRepository<ProductAndServiceClassificationEntity> {

    boolean existsByUid(long id);

    List<ProductAndServiceClassificationEntity> findByProductClassificationUid(Long productClassificationId);

    List<ProductAndServiceClassificationEntity> findByServiceClassificationUid(Long serviceClassificationId);

}
