package com.swifttech.sr.ps.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.entity.DynamicProductAttributeEntity;

import java.util.List;

public interface DynamicProductAttributeRepository extends BaseRepository<DynamicProductAttributeEntity> {

    boolean existsByUid(long id);

    List<DynamicProductAttributeEntity> findByProductUid(Long productId);

    void deleteByProductUid(Long productId);

}
