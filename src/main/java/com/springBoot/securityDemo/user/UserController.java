package com.springBoot.securityDemo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/getUserByUserName")
    public Optional<Users> getUserByUserName(@RequestParam("userName") String userName){
        Optional<Users> byUserName = userRepository.findByEmail(userName);
        return byUserName;
    }
    @PostMapping("/add")
    public Users addUser(@RequestBody UserRequestDTO user){
        Users users = userService.addUser(user);
        return users;
    }
    @GetMapping("/all")
    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

}
