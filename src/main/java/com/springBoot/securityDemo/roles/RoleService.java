package com.springBoot.securityDemo.roles;

import com.springBoot.securityDemo.permissions.Permission;
import com.springBoot.securityDemo.permissions.PermissionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PermissionRepository permissionRepository;

    public Role addRole(RoleRequestDTO requestDTO) {
        Role role=new Role();
        List<Permission> permissionList = requestDTO.getPermissionList().stream().map(permissionlist ->
                        permissionRepository.findById(permissionlist).orElseThrow(()
                                -> new IllegalArgumentException("permission not found with id " + permissionlist)))
                .collect(Collectors.toList());
        role.setPermissionList(permissionList);
        role.setRoleName(requestDTO.getRoleName());
        roleRepository.save(role);
        return role;
    }
}
