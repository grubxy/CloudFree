package com.xy.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstructionRepository extends JpaRepository<Construction, String> {
    Page<Construction> findConstructionByEnumConstructStatus(EnumConstructStatus enumConstructStatus, Pageable pageable);
}
