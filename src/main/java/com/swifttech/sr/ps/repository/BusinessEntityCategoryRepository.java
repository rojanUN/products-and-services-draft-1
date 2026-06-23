package com.swifttech.sr.ps.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.entity.BusinessEntityCategoryEntity;

public interface BusinessEntityCategoryRepository extends BaseRepository<BusinessEntityCategoryEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);

}
