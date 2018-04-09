package com.xy;

import com.xy.domain.Staff;
import com.xy.service.StaffService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StaffTest {

    @Autowired
    private StaffService staffService;

    @Test
    public void addStaff()throws Exception {
        Staff staff = new Staff();
        staff.setStaffName("xiaoye");
        staffService.addStaff(staff);
    }
}
