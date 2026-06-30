package com.swifttech.edx.sr.ps.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.edx.sr.ps.entity.ProductTypeEntity;

import java.util.List;

public interface ProductTypeRepository extends BaseRepository<ProductTypeEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);

    List<ProductTypeEntity> findByProductClassificationUid(Long productClassificationId);

}
