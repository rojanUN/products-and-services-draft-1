package com.swifttech.sr.ps.schema.repository;

import com.swifttech.edx.dm.repository.BaseRepository;
import com.swifttech.sr.ps.schema.entity.DynamicProductAttributeEntity;

import java.util.List;

public interface DynamicProductAttributeRepository extends BaseRepository<DynamicProductAttributeEntity> {

    boolean existsByUid(long id);

    List<DynamicProductAttributeEntity> findByProductUid(Long productId);

    void deleteByProductUid(Long productId);

}
