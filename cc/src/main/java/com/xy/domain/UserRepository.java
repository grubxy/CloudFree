package com.xy.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "select p from User p where p.username = :name")
	User findByUsername(@Param("name") String name);
}
