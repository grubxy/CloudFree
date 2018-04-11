package com.xy.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OriginRepository extends JpaRepository<Origin, Integer> {
    Origin findByName(String name);
}
