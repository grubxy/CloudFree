package com.xy;

import com.xy.domain.EnumRole;
import com.xy.domain.Role;
import com.xy.domain.RoleRepository;
import com.xy.domain.User;
import com.xy.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SqlInitApplicationRunner implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthService authService;
    
    @Override
    public void run(ApplicationArguments var1) throws Exception {
        log.info("init db....");
        Role role = new Role();
        role.setRid(EnumRole.SYSTEM.getId());
        role.setRole(EnumRole.SYSTEM.getRole());
        roleRepository.save(role);

        role = new Role();
        role.setRid(EnumRole.FLOW.getId());
        role.setRole(EnumRole.FLOW.getRole());
        roleRepository.save(role);

        role = new Role();
        role.setRid(EnumRole.STORE.getId());
        role.setRole(EnumRole.STORE.getRole());
        roleRepository.save(role);


        User user = new User();
        user.setUsername("yexiaoyong");
        user.setPassword("123456");
        user.setOwner("叶晓勇");
        role.setRid(EnumRole.SYSTEM.getId());
        role.setRole(EnumRole.SYSTEM.getRole());
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        user.setRoles(roleList);
        authService.register(user);

        User user1 = new User();
        user1.setUsername("xiaohei");
        user1.setPassword("123456");
        user1.setOwner("小黑");
        role.setRid(EnumRole.FLOW.getId());
        role.setRole(EnumRole.FLOW.getRole());
        roleList = new ArrayList<>();
        roleList.add(role);
        user1.setRoles(roleList);
        authService.register(user1);
    }
}
