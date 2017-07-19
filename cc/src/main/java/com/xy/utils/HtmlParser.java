package com.xy.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xy.model.ParserDetails;

public class HtmlParser {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public ParserDetails parserContent(String content) {
		ParserDetails p = new ParserDetails();
		
		Document doc = Jsoup.parse(content);
		// 选择背景图片
		Element img = doc.select("img[src").first();
		if (img != null) {
			String imgUrl = img.attr("src");
			log.info("img src: " + imgUrl);
			p.setPicUrl(imgUrl);
		}	
		
		// 解析文章文字内容
		StringBuffer t = new StringBuffer();
		Elements text = doc.select("p");
		if (text != null) {
			for (Element n : text) {
				log.info("text: " + n.text());
				t.append(n.text());
			}
		}	
		p.setContent(t.toString());
		log.info("content: " + p.getContent());
		return p;
	}
}
