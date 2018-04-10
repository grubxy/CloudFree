package com.xy;

import com.xy.domain.Product;
import com.xy.domain.Seq;
import com.xy.domain.Staff;
import com.xy.service.BaseDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BaseDataTest {

    @Autowired
    private BaseDataService baseDataService;

    // 新增产品
    @Test
    public void addProduct() throws Exception {
        Product product = new Product();
        product.setProductName("产品名称1 小刀");
        baseDataService.addProduct(product);

        //System.out.println(baseDataService.getAllProduct(0, 10).toString());
    }

    // 新增 工序
    @Test
    public void addSeqByProductId() throws Exception {
        Seq seq = new Seq();
        seq.setSeqCost(0.001f);
        seq.setSeqIndex(1);
        seq.setSeqName("洗");

        baseDataService.addSeqByProductId(1, seq);

        seq = new Seq();
        seq.setSeqCost(0.0002f);
        seq.setSeqIndex(2);
        seq.setSeqName("刷");

        baseDataService.addSeqByProductId(1, seq);

    }

    // 新增默认员工
    @Test
    public void addDefaultStaff() throws Exception {
        Staff staff = new Staff();

        staff.setIdStaff(1);

        baseDataService.addStaffBySeqId(1, staff);

//        String resp = this.mockMvc
//                .perform(post("/workflow/production/add")
//                        .characterEncoding("UTF-8")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(JSON.toJSONString(pr).getBytes()))
//                .andReturn().getResponse().getContentAsString();
    }
}