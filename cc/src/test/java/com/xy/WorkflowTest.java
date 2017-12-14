package com.xy;

import com.alibaba.fastjson.JSON;
import com.xy.controller.WorkflowController;
import com.xy.dao.produce.Construct;
import com.xy.dao.produce.Production;
import com.xy.service.ConstructService;
import com.xy.service.ProductionService;
import com.xy.utils.SnowFlake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WorkflowTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkflowController workflowController;

    @Autowired
    private ProductionService productionService;

    @Autowired
    private  ConstructService constructService;


    @Test
    public void addProduction() throws Exception {
        SnowFlake sf = new SnowFlake(0, 0);
        // 添加生产流程
        assertThat(workflowController).isNotNull();
        Production pr = new Production();
        // 订单名
        pr.setName("给客户1的订单");
        // 单号
        pr.setPid(sf.nextId());
        // 日期
        pr.setDate(new Date());
        // 实际生产数量
        pr.setFact_counts(0);
        // 次品数量
        pr.setErr_counts(0);
        // 目标数量
        pr.setDst_counts(1000);
        // 完成数量
        pr.setCmpl_counts(0);
        // 状态
        pr.setState(0);
        // 详情
        pr.setDetail("这是一个测试的生产单...");
        String resp = this.mockMvc
                .perform(post("/workflow/production/add")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(pr).getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println("production add:" + resp);
    }

    @Test
    public void addConstruction() throws Exception{

        Construct cs = new Construct();
        SnowFlake sf = new SnowFlake(0, 0);

        cs.setCid(sf.nextId());
        cs.setCmpl_counts(100);
        cs.setErr_counts(2);
        cs.setDst_counts(200);
        cs.setDate(new Date());
        constructService.saveConstructByPid(138629067074174976L,cs);
    }
}
