package com.xy;

import com.alibaba.fastjson.JSON;
import com.xy.domain.*;
import com.xy.service.ProductionFlowService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductionFlowTest {

    @Autowired
    private ProductionFlowService productionFlowService;

    @Autowired
    private MockMvc mockMvc;

    private static final String flowId= "181921975780769792";

    // 新增生产流程
    @Test
    public void addFlow() throws Exception {

        ProductionFlow productionFlow = new ProductionFlow();

        Product product = new Product();
        product.setIdProduct(1);

        productionFlow.setProduct(product);

        productionFlow.setDstCounts(1750);

        String resp = this.mockMvc
                .perform(post("/workflow/addflow")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(productionFlow).getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();

    }

    // 获取所有生产流程
    @Test
    public void getFlow() throws Exception {
        String resp = this.mockMvc.perform(get("/workflow/getflow?page=0&size=10"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    // 获取产品工序
    @Test
    public void getSeq() throws Exception {
        String resp = this.mockMvc.perform(get("/workflow/getSeq/" + flowId))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    // 获取工序详情
    @Test
    public void getSeqInfo() throws Exception {
        String resp = this.mockMvc.perform(get("/workflow/getSeqInfo/" + flowId))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    // 新增施工单
    @Test
    public void addConstructByFlow() throws Exception {

        Construction construction = new Construction();

        construction.setDstCount(1000);

        Seq seq = new Seq();
        seq.setIdSeq(1);
        construction.setSeq(seq);

        Staff staff = new Staff();
        staff.setIdStaff(1);
        construction.setStaff(staff);

        String resp = this.mockMvc
                .perform(post("/workflow/addConstruction/" + flowId)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(construction).getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getConstructions() throws Exception {
        String resp = this.mockMvc.perform(get("/workflow/getConstruction/" + flowId))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }


}
