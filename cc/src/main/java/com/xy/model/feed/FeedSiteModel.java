package com.xy.model.feed;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class FeedSiteModel {
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 private Integer id;
	 
	 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	 private Date createDate;
	 
	 private String feedType;
	 
	 private String url;
	 
	 public Integer getId(){
		 return id;
	 }
	 
	 public void setId(Integer id) {
		 this.id = id;
	 }
	 
	 public Date getCreateDate() {
		 return createDate;
	 }
	 
	 public void setCreateDate(Date createData) {
		 this.createDate = createData;
	 }
	 
	 public String getFeedType() {
		 return feedType;
	 }
	 
	 public void setFeedType(String feedType) {
		 this.feedType = feedType;
	 }
	 
	 public String getUrl() {
		 return url;
	 }
	 
	 public void setUrl(String url) {
		 this.url = url;
	 }
}

