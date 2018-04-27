package com.xy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
    private MockMvc mockMvc;

    private static final String flowId= "182273233192484864";

    private static final String consId = "182274367034490880";

    // 新增生产流程
    @Test
    public void addFlow() throws Exception {

        ProductionFlow productionFlow = new ProductionFlow();

        Product product = new Product();
        product.setIdProduct(1);

        productionFlow.setProduct(product);

        productionFlow.setDstCounts(1750);

        String resp = this.mockMvc
                .perform(post("/flow")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(productionFlow).getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();

    }

    // 获取所有生产流程
    @Test
    public void getFlow() throws Exception {
        String resp = this.mockMvc.perform(get("/flow?page=0&size=0"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    // 获取产品工序
    @Test
    public void getSeq() throws Exception {
        String resp = this.mockMvc.perform(get("/flow/187280196921982976/seq/"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    // 获取工序详情
    @Test
    public void getSeqInfo() throws Exception {
        String resp = this.mockMvc.perform(get("/flow/187280196921982976/seqinfo" ))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    // 新增施工单
    @Test
    public void addConstructByFlow() throws Exception {

        Construction construction = new Construction();

        construction.setDstCount(2);

        Seq seq = new Seq();
        seq.setIdSeq(1);
        construction.setSeq(seq);

        Staff staff = new Staff();
        staff.setIdStaff(1);
        construction.setStaff(staff);

        String resp = this.mockMvc
                .perform(post("/flow/187349472194330624/construction")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(construction).getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    // 获取工单
    @Test
    public void getConstructions() throws Exception {
        String resp = this.mockMvc.perform(get("/flow/187349472194330624/construction"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    // 获取某个状态的工单
    @Test
    public void getConstructionByStatus() throws Exception {
        String resp = this.mockMvc.perform(get("/workflow/getConstructionByStatus?status=3&page=0&size=10"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    /** 对某个工单进行处理 **/

    // 出库
    @Test
    public void setWorking() throws Exception {
        JSONObject object = new JSONObject();
        object.put("status", EnumConstructStatus.WORKING.ordinal());
        object.put("idHouse",0);
        object.put("error",0);
        object.put("cmpl",0);

        String resp = this.mockMvc
                .perform(post("/construction/xx/")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(object.toJSONString().getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    // 完工
    @Test
    public void completeWorking() throws Exception {
        JSONObject object = new JSONObject();
        object.put("status", EnumConstructStatus.COMPLETE.ordinal());
        object.put("idHouse",0);
        object.put("error",2);
        object.put("cmpl",998);

        String resp = this.mockMvc
                .perform(post("/construction/xx/")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(object.toJSONString().getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    // 入库
    @Test
    public void storeWorking() throws Exception {
        JSONObject object = new JSONObject();
        object.put("status", EnumConstructStatus.STORED.ordinal());
        object.put("idHouse",1);
        object.put("error",0);
        object.put("cmpl",0);

        String resp = this.mockMvc
                .perform(post("/workflow/setStatus/" + consId)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(object.toJSONString().getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

}
