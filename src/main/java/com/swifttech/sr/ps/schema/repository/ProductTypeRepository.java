package com.swifttech.sr.ps.schema.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.schema.entity.ProductTypeEntity;

import java.util.List;

public interface ProductTypeRepository extends BaseRepository<ProductTypeEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);

    List<ProductTypeEntity> findByProductClassificationUid(Long productClassificationId);

}
