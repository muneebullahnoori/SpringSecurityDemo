package com.springBoot.securityDemo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/getUserByUserName")
    public Optional<Users> getUserByUserName(@RequestParam("userName") String userName){
        Optional<Users> byUserName = userRepository.findByUserName(userName);
        return byUserName;
    }
    @PostMapping("/add")
    public Users addUser(@RequestBody Users user){
        Users savedUser = userRepository.save(user);
        return savedUser;
    }

}
