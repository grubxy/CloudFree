package com.xy;

import com.xy.utils.SnowFlake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SnowFlakeTest {

    @Test
    public void snowFlakeTest() {
        SnowFlake sf = SnowFlake.getInstance();
        for (int i=0; i < 100; i++) {
            System.out.println("code: " + sf.nextId() + "\n");
        }
    }
}
