package com.xy;

import com.alibaba.fastjson.JSON;
import com.xy.domain.Role;
import com.xy.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addUser() throws Exception {
        User user = new User();
        user.setUsername("yexiaoyong11");
        user.setPassword("351351");
        user.setOwner("ss");
        Role role = new Role();
        role.setRid(2);
        List<Role> roleSet = new ArrayList<Role>();
        roleSet.add(role);
        role = new Role();
        role.setRid(1);
        roleSet.add(role);

        user.setRoles(roleSet);

        System.out.println("request: " + JSON.toJSONString(user));

        String resp = this.mockMvc
                .perform(post("/auth/register")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(user).getBytes())).andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getUserList() throws Exception {
        String resp = this.mockMvc.perform(get("/userlist")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void deleteUser() throws Exception {
        String resp = this.mockMvc.perform(get("/delUser/1")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
