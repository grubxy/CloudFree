package com.xy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

import com.xy.service.FeedInterfaceService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScaleTest {

	private String json = "{\"type\":\"1\", \"width\":\"20\", \"height\":\"20\",\"rate\":\"0.5\",\"imgData\":\"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\"}";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Resource(name = "FeedServiceImpl")
	private FeedInterfaceService FeedService;
	
	//@Test
	public void scale() throws Exception {
		String response = this.mockMvc.perform(
				post("/scale", "json").characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.getBytes()))
				.andReturn().getResponse().getContentAsString();
		System.out.println("mockmvc response------------------<<" + response);
	}
	
	@Test
	public void feedTest() throws Exception{
		System.out.println("----->>rss test start...");
		//FeedService.getRss();
		//FeedService.getFeed("2017-06-30 09:00:00");
//		this.mockMvc.perform(
//				get("/getfeed")
//				.param("time", "2017-06-10 09:00:00")
//				.param("type", "财经")
//				)
//		.andDo(print())
//		.andExpect(status().isOk())
//        .andExpect(content().string(containsString("")));
		
		// delete feed
		this.mockMvc.perform(
				get("/deletefeed")
				.param("time", "2017-07-11 23:16:22.0"))
		.andDo(print()).andExpect(status().isOk());	
	}
}
