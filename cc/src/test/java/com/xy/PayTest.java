package com.xy;

import com.github.javafaker.Faker;
import com.xy.controller.PayController;
import com.xy.dao.pay.Employee;
import com.xy.service.PayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.alibaba.fastjson.JSON;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PayTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PayController payController;


    @Test
    public void addEmployee() throws Exception{

        assertThat(payController).isNotNull();
        Faker faker = new Faker(new Locale("zh-CN"));

        for (int i = 0; i < 2; i++){

            Employee em = new Employee();
            em.setName(faker.name().fullName());
            em.setPay(faker.number().numberBetween(1000, 2000));

            System.out.println("test:"+ JSON.toJSONString(em));
            String resp = this.mockMvc
                    .perform(post("/pay")
                            .characterEncoding("UTF-8")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JSON.toJSONString(em).getBytes())
                    ).andReturn().getResponse().getContentAsString();
            System.out.println("add test resp:" + resp);
        }
    }
    @Test
    public void selectEmployee() throws  Exception {

        String resp = this.mockMvc.perform(get("/pay/employee?page=1&size=10"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("select resp: " + resp);
    }
}
