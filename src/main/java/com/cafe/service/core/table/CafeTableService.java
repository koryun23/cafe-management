package com.cafe.service.core.table;

import com.cafe.entity.table.CafeTable;
import com.cafe.entity.table.CafeTableStatusType;

import java.util.Optional;

public interface CafeTableService {

    CafeTable create(CafeTableCreationParams params);

    CafeTable getById(Long id);

    Optional<CafeTable> findById(Long id);

    CafeTable markAs(Long id, CafeTableStatusType status);

    Optional<CafeTable> findByCode(String code);

    boolean existsByCode(String code);
}
