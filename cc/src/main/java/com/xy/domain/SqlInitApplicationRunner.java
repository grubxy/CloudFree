package com.xy.domain;

import com.xy.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
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
        authService.register(user);
    }
}
