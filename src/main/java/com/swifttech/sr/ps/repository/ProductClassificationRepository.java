package com.swifttech.sr.ps.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.entity.ProductClassificationEntity;

public interface ProductClassificationRepository extends BaseRepository<ProductClassificationEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);

}
