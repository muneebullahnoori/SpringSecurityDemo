package com.springBoot.securityDemo.user;

import com.springBoot.securityDemo.roles.Role;
import com.springBoot.securityDemo.roles.RoleRepository;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;


    public Users addUser(UserRequestDTO user) {
//        List<Role> roleList = roleRepository.findByIdIn(user.getRoleId());
//        Users map = modelMapper.map(user, Users.class);
        Users map=new Users();
        map.setUserName(user.getUserName());
        map.setPassword(user.getPassword());
        map.setFullName(user.getFullName());
        List<Role> roles = user.getRoleId().stream()
                .map(roleId -> roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException("Role not found for id: " + roleId)))
                .collect(Collectors.toList());
        map.setRoleList(roles);

        Users save = userRepository.save(map);
        return save;
    }







}
