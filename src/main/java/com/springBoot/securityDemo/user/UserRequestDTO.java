package com.springBoot.securityDemo.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserRequestDTO {
    private String userName;
    private String password;
    private String fullName;
    private List<Long> roleId;
}
