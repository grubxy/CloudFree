package com.xy;

import com.alibaba.fastjson.JSON;
import com.xy.domain.Product;
import com.xy.domain.Seq;
import com.xy.domain.Staff;
import com.xy.service.BaseDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BaseDataTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BaseDataService baseDataService;

    // 新增产品
    @Test
    public void addProduct() throws Exception {
        Product product = new Product();
        product.setProductName("产品名称1 小刀");

        String resp = this.mockMvc
        .perform(post("/data/addProduct")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(product).getBytes())).andDo(print())
        .andReturn().getResponse().getContentAsString();
    }

    // 获取产品列表
    @Test
    public void getProduct() throws Exception {

        String resp = this.mockMvc.perform(get("/data/getProductsList")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("no page resp: " + resp);


        resp = this.mockMvc.perform(get("/data/getProducts?page=0&size=10")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("page resp: " + resp);
    }

    // 新增 工序
    @Test
    public void addSeqByProductId() throws Exception {
        Seq seq = new Seq();
        seq.setSeqCost(0.001f);
        seq.setSeqName("洗");

        String resp = this.mockMvc
                .perform(post("/data/addSeq/1")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(seq).getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();

        seq = new Seq();
        seq.setSeqCost(0.0002f);
        seq.setSeqName("刷");

        resp = this.mockMvc
                .perform(post("/data/addSeq/1")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(seq).getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    public void getSeqByProductId() throws Exception {
        String resp = this.mockMvc.perform(get("/data/getSeq/1"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    // 新增默认员工
    @Test
    public void addDefaultStaff() throws Exception {
        Staff staff = new Staff();

        staff.setIdStaff(1);

        String resp = this.mockMvc
                .perform(post("/data/addStaff/1")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(staff).getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    // 获取员工
    @Test
    public void getDefaultStaffBySeqId() throws Exception {
        String resp = this.mockMvc.perform(get("/data/getStaff/1"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    // 新增员工
    @Test
    public void addStaff() throws Exception {
        Staff staff = new Staff();

        staff.setStaffName("xiaowang");

        String resp = this.mockMvc
                .perform(post("/data/addStaff")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(staff).getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();
    }


    @Test
    public void addBaseData() throws Exception {
        this.addProduct();
        this.addSeqByProductId();
        this.addStaff();
        this.addDefaultStaff();
    }

}
