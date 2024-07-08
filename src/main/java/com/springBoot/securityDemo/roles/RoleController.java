package com.springBoot.securityDemo.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;
    @PostMapping("/add")
    public Role addRole(@RequestBody Role role){
        Role savedRole = roleRepository.save(role);
        return savedRole;
    }
}
