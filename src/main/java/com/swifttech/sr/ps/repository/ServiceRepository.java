package com.swifttech.sr.ps.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.entity.ServiceEntity;

import java.util.List;

public interface ServiceRepository extends BaseRepository<ServiceEntity> {

    boolean existsByUid(long id);
    boolean existsByName(String name);
    boolean existsByNameAndUidNot(String name, Long uid);
    boolean existsByNameAndServiceClassificationUid(String name, Long serviceClassificationId);
    boolean existsByNameAndServiceClassificationUidAndUidNot(String name, Long serviceClassificationId, Long uid);

    List<ServiceEntity> findByServiceClassificationUid(Long serviceClassificationId);

    List<ServiceEntity> findByServiceCategoryUid(Long categoryId);

}
