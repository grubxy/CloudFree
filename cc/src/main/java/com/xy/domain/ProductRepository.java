package com.xy.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface ProductRepository extends JpaRepository<Product, Integer>, QueryDslPredicateExecutor<Product> {
}
