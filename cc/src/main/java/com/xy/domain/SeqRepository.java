package com.xy.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface SeqRepository extends JpaRepository<Seq, Integer>, QueryDslPredicateExecutor<Seq> {
    Seq findSeqBySeqIndexEquals(int index);
}
