package com.swifttech.edx.sr.ps.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.edx.sr.ps.entity.ProductComponentEntity;

import java.util.List;

public interface ProductComponentRepository extends BaseRepository<ProductComponentEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);

    List<ProductComponentEntity> findByProductTypeUid(Long productTypeId);

}
