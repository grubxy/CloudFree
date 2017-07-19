package com.xy.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.xy.model.ParserDetails;
import com.xy.model.feed.FeedInfoModel;
import com.xy.model.feed.FeedInfoRepository;
import com.xy.model.feed.FeedSiteModel;
import com.xy.model.feed.FeedSiteRepository;
import com.xy.service.FeedInterfaceService;
import com.xy.utils.FastDFSUtil;
import com.xy.utils.HtmlParser;

@Service("FeedServiceImpl")
public class FeedServiceImpl implements FeedInterfaceService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FeedInfoRepository feedRep;
	
	@Autowired
	private FeedSiteRepository siteRep;
	
	public void getRss() {
		// 获取数据库中保存的url
		for (FeedSiteModel siteList : siteRep.findAll()) {
			log.info("site url: " + siteList.getUrl());
			getRss(siteList.getUrl(), siteList.getFeedType());
		}	
	}
	
	private void getRss(String link, String feedType){		
		//http 请求
		CloseableHttpClient client = HttpClients.createMinimal();
		HttpUriRequest request = new HttpGet(link);
	    CloseableHttpResponse response;
		try {
			// 获取 entity
			response = client.execute(request);
			InputStream stream;	
			log.info("encoding: " + response.getEntity().getContentEncoding());
			if (response.getEntity().getContentEncoding() != null &&
					response.getEntity().getContentEncoding().toString().contains("gzip")) {
				GzipDecompressingEntity gzipEntity = new GzipDecompressingEntity(response.getEntity());
				log.info("gzip....");
				stream = gzipEntity.getContent();
			} else {
				log.info("normal...");
				stream = response.getEntity().getContent();
			}
			//解析 xml
		    SyndFeedInput input = new SyndFeedInput();		   
		    try {
				SyndFeed feed = input.build(new XmlReader(stream));
				log.info("源地址: " + feed.getTitle());
				log.info("源描述: " + feed.getDescription());					
				List<SyndEntry> entry = feed.getEntries();
				for (SyndEntry en : entry) {
					log.info("标题: " +  en.getTitle());
					log.info("URL: " + en.getLink());
					log.info("文章发布时间: " + en.getPublishedDate());
					log.info("文章内容: " + en.getDescription().getValue());
					
					// 解析文章内容
					HtmlParser p = new HtmlParser();
					ParserDetails details = p.parserContent(en.getDescription().getValue());
					
					// 写数据库
					FeedInfoModel feedInfo = new FeedInfoModel();			
					// 查询数据库  判断是否存在
					List<FeedInfoModel> oldList = feedRep.findByTitle(en.getTitle());
					if (oldList.size() == 0) {
						// 写文件
						String desUrl = "";
						try {
							FastDFSUtil util = new FastDFSUtil("fdfs_client.conf");
							desUrl = util.uploadFile(details.getContent().getBytes("UTF-8"), "txt");		
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// 写数据库
						feedInfo.setSiteTitle(feed.getTitle());
						feedInfo.setTitle(en.getTitle());
						feedInfo.setUrl(en.getLink());
						feedInfo.setPicUrl(details.getPicUrl());
						feedInfo.setPubData(en.getPublishedDate().toString());
						feedInfo.setDesc(desUrl);
						feedInfo.setCreateDate(new Date());
						feedInfo.setFeedType(feedType);
						feedRep.save(feedInfo);
					}
				}				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FeedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	// 获取对应时间后的消息
	public List<FeedInfoModel> getFeed(String time) {
		log.info("get feed start time: " + time);
		// 解析时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = formatter.parse(time);	
			// 数据库查找新于该时间的更新
			List<FeedInfoModel> newList = feedRep.findByDateUp(date);		
			for (FeedInfoModel x : newList) {
				log.info("title: " + x.getTitle());
				log.info("create data: " + x.getCreateDate().toString());
			}	
			return newList;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// 删除内容
	public void deleteFeed(String time) {
		log.info("delete feed time: " + time);
		
		// 解析时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = formatter.parse(time);
			List<FeedInfoModel> newList = feedRep.findByDateDown(date);
			for (FeedInfoModel x: newList) {		
				// 删除数据库
				log.info("deleteing...tile: " + x.getTitle());
				feedRep.delete(x);		
				// 删除存储url
				// 暂时不删除，fastdfs换服务器后考虑 
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
