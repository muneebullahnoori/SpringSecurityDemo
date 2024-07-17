package com.springBoot.securityDemo.exceptions;

import javax.naming.AuthenticationException;

public class UserStatusExcetion extends AuthenticationException{
    public UserStatusExcetion(String message){
        super(message);
    }
}
