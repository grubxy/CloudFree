package com.xy.dao.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

	@Query(value = "select p from User p where p.username = :name")
	User findByUsername(@Param("name") String name);
}
