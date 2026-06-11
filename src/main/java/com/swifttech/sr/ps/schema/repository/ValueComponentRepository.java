package com.swifttech.sr.ps.schema.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.schema.entity.ValueComponentEntity;

import java.util.List;

public interface ValueComponentRepository extends BaseRepository<ValueComponentEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);

    List<ValueComponentEntity> findByNameContainingIgnoreCase(String name);

}
