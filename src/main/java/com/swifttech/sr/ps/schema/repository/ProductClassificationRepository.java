package com.swifttech.sr.ps.schema.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.schema.entity.ProductClassificationEntity;

import java.util.List;

public interface ProductClassificationRepository extends BaseRepository<ProductClassificationEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);

    List<ProductClassificationEntity> findByParentClassificationIsNull();

}
