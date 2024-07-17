package com.springBoot.securityDemo.authentication;

import com.springBoot.securityDemo.exceptions.UserStatusExcetion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) throws UserStatusExcetion {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return authenticationResponse;
    }


}
