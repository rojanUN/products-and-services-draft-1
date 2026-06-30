package com.swifttech.edx.sr.ps.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.edx.sr.ps.entity.ProductClassificationEntity;

import java.util.List;

public interface ProductClassificationRepository extends BaseRepository<ProductClassificationEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);

    List<ProductClassificationEntity> findByParentClassificationIsNull();

    List<ProductClassificationEntity> findByParentClassificationUid(Long parentId);

}
