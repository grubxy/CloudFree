package com.xy;

import com.alibaba.fastjson.JSON;
import com.xy.controller.ManageController;
import com.xy.dao.manage.Material;
import com.xy.dao.manage.Technics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ManageTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ManageController manageController;


    @Test
    public void addMaterial() throws Exception{

        assertThat(manageController).isNotNull();
        Material mt = new Material();
        mt.setName("材料1");
        mt.setSpec("规格1");
        String resp = this.mockMvc
                .perform(post("/manage/material/add")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(mt).getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println("material add:" + resp);
    }
    @Test
    public void selectMaterial() throws  Exception {
        String resp = this.mockMvc.perform(get("/manage/material?page=0&size=10"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("select resp: " + resp);
    }

    @Test
    public  void deleteMaterial() throws Exception {
        Material mt = new Material();
        mt.setCode(1);
        String resp = this.mockMvc
                .perform(post("/manage/material/del")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(mt).getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println("material del:" + resp);
    }

    @Test
    public  void addProcedure()throws  Exception {
        assertThat(manageController).isNotNull();
        Technics pr = new Technics();
        pr.setName("工艺1");
        String resp = this.mockMvc
                .perform(post("/manage/procedure/add")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(pr).getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println("add procedure: " + resp);
    }

    @Test
    public void selectProcedure() throws  Exception {
        String resp = this.mockMvc.perform(get("/manage/procedure?page=0&size=10"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("procedure select resp: " + resp);
    }

    @Test
    public  void delProcedure() throws Exception {
        Technics tc = new Technics();
        tc.setCode(1);
        String resp = this.mockMvc
                .perform(post("/manage/procedure/del")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(tc).getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println("procedure del:" + resp);
    }
}
