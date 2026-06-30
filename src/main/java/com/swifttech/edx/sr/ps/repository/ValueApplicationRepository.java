package com.swifttech.edx.sr.ps.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.edx.sr.ps.entity.ValueApplicationEntity;

public interface ValueApplicationRepository extends BaseRepository<ValueApplicationEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);

}
