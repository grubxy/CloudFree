package com.xy.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
    Material findMaterialByName(String name);
}
