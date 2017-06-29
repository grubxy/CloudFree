package com.xy.model.feed;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class FeedInfoModel {
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 private Integer id;
	 
	 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	 private Date createDate;
	 
	 private String feedType;
	 
	 private String siteTitle;
	 
	 private String siteDescription;
	 
	 private String title;
	 
	 private String picUrl;
	 
	 private String url;
	 
	 private String pubData;
	 
	 private String details;
	 
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
	 
	 public void setFeedType(String feedType) {
		 this.feedType = feedType;
	 }
	 
	 public String getFeedType() {
		 return this.feedType;
	 }
	 
	 public String getSiteTitle() {
		 return siteTitle;
	 }
	 
	 public void setSiteTitle(String siteTitle) {
		 this.siteTitle = siteTitle;
	 }
	 
	 public String getSiteDescription() {
		 return siteDescription;
	 }
	 
	 public void setSiteDescription(String siteDescription) {
		 this.siteDescription = siteDescription;
	 }
	 
	 public String getTitle() {
		 return title;
	 }
	 
	 public void setTitle(String title) {
		 this.title = title;
	 }
	 
	 public String getPicUrl() {
		 return picUrl;
	 }
	 
	 public void setPicUrl(String picUrl) {
		 this.picUrl = picUrl;
	 }
	 
	 public String getUrl() {
		 return url;
	 }
	 
	 public void setUrl(String url) {
		 this.url = url;
	 }
	 
	 public String getPubData() {
		 return pubData;
	 }
	 
	 public void setPubData(String pubData) {
		 this.pubData = pubData;
	 }
	 
	 public String getDetails() {
		 return details;
	 }
	 
	 public void setDesc(String details) {
		 this.details = details;
	 }
}
