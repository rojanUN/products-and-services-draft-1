package com.swifttech.sr.ps.schema.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.schema.entity.ValueComponentTypeEntity;

public interface ValueComponentTypeRepository extends BaseRepository<ValueComponentTypeEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);

}
