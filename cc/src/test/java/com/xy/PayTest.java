package com.xy;

import com.xy.dao.pay.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.alibaba.fastjson.JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PayTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void add() throws Exception{
        Employee em = new Employee();
        em.setName("小王");
        em.setPay(1000);

        String resp = this.mockMvc
                .perform(post("/add", "json")
                        .characterEncoding("UTI-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(em).getBytes())
                ).andReturn().getResponse().getContentAsString();
        System.out.println("add test resp:" + resp);
    }
}
