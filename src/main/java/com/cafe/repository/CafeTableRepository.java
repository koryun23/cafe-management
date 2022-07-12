package com.cafe.repository;

import com.cafe.entity.table.CafeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CafeTableRepository extends JpaRepository<CafeTable, Long> {

    Optional<CafeTable> findByCode(String code);

    boolean existsByCode(String code);
}
