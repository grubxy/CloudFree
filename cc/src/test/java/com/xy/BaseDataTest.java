package com.xy;

import com.alibaba.fastjson.JSON;
import com.xy.domain.*;
import com.xy.service.BaseDataService;
import com.xy.service.StaffService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
        product.setProductName("产品名1 小刀");

        String resp = this.mockMvc
        .perform(post("/product")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(product).getBytes())).andDo(print())
        .andReturn().getResponse().getContentAsString();
    }

    // 获取产品列表
    @Test
    public void getProduct() throws Exception {

        String resp = this.mockMvc.perform(get("/product?page=0&size=0")
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

        System.out.println(JSON.toJSONString(seq));
        String resp = this.mockMvc
                .perform(post("/product/1/seq")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(seq).getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();

        seq = new Seq();
        seq.setSeqCost(0.0002f);
        seq.setSeqName("刷");

        resp = this.mockMvc
                .perform(post("/product/1/seq")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(seq).getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    public void getSeqByProductId() throws Exception {
        String resp = this.mockMvc.perform(get("/product/1/seq?page=0&size=0"))
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

        staff.setStaffName("晓xi");
        staff.setEnumStaffStatus(EnumStaffStatus.POSITIONING);

        String resp = this.mockMvc
                .perform(post("/staff")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(staff).getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    // 获取员工
    @Test
    public void getStaff()throws Exception {
        String resp = this.mockMvc.perform(get("/staff?page=0&size=0&status=0"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    // 获取原料
    @Test
    public void getOrigins() throws Exception {
        String resp = this.mockMvc.perform(get("/house/1"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    // 给仓库添加物料
    @Test
    public void addOriginByHouseId()throws Exception {

//        String body = "{\"name\":\"测试材料\", \"counts\":\"111\"}";

        String body = "{\"name\":\"小刀_原始材料\", \"counts\":\"7\"}";

        String resp = this.mockMvc
                .perform(post("/house/1/origin")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void delOriginByHouseId() throws Exception {
        String body = "{\"counts\":\"8\"}";

        String resp = this.mockMvc
                .perform(delete("/house/1/origin/1")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    public void addHouse() throws Exception {
        House house = new House();
        house.setHouseName("仓库1号");

        String resp = this.mockMvc
                .perform(post("/data/addHouse")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(house).getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getHouseList()throws Exception {
        String resp = this.mockMvc.perform(get("/house?page=0&size=0"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void addBaseData() throws Exception {
        this.addProduct();
        this.addSeqByProductId();
        this.addStaff();
        this.addDefaultStaff();
        this.addHouse();
    }

    @Autowired
    private StaffService staffService;

    @Test
    public void getStaffSalary() throws Exception {
        staffService.getStaffSalaryByName(
                0, 10, "xx", new Date(), new Date()
        );
    }

}
