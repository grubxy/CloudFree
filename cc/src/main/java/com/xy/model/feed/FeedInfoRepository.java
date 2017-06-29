package com.xy.model.feed;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FeedInfoRepository extends CrudRepository<FeedInfoModel, Long> {

	@Query(value = "select p from FeedInfoModel p where p.title = :title")
	List<FeedInfoModel> findByTitle(@Param("title") String title);
}
