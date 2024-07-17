package com.springBoot.securityDemo.roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleRequestDTO {
    private String roleName;
    private List<Long> permissionList;
}