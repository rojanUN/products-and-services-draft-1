package com.swifttech.edx.sr.ps.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.edx.sr.ps.entity.ValueComponentEntity;

import java.util.List;

public interface ValueComponentRepository extends BaseRepository<ValueComponentEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);

    List<ValueComponentEntity> findByNameContainingIgnoreCase(String name);

}
