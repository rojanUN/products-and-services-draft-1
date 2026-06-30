package com.swifttech.edx.sr.ps.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.edx.sr.ps.entity.ProductAndServiceClassificationEntity;

import java.util.List;

public interface ProductAndServiceClassificationRepository extends BaseRepository<ProductAndServiceClassificationEntity> {

    boolean existsByUid(long id);

    List<ProductAndServiceClassificationEntity> findByProductClassificationUid(Long productClassificationId);

    List<ProductAndServiceClassificationEntity> findByServiceClassificationUid(Long serviceClassificationId);

}
