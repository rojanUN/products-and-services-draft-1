package com.swifttech.edx.sr.ps.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.edx.sr.ps.entity.ServiceClassificationEntity;

import java.util.List;

public interface ServiceClassificationRepository extends BaseRepository<ServiceClassificationEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);

    List<ServiceClassificationEntity> findByParentClassificationIsNull();

    List<ServiceClassificationEntity> findByParentClassificationUid(Long parentId);

}
