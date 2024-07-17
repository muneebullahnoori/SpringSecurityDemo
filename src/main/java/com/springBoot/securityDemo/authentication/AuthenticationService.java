package com.springBoot.securityDemo.authentication;

import com.springBoot.securityDemo.exceptions.UserStatusExcetion;
import com.springBoot.securityDemo.securityConfig.JwtUtil;
import com.springBoot.securityDemo.user.UserRepository;
import com.springBoot.securityDemo.user.Users;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Data
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserStatusExcetion {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));
        Users user = userRepository.findByEmail(request.getEmail()).orElseThrow(()
                -> new UsernameNotFoundException("user not found with email" + request.getEmail()));
        if (!user.isActivate()){
            throw new UserStatusExcetion("this user is blocked");
        }
        String jwtToken = jwtUtil.generateToken(user);
        AuthenticationResponse authenticationResponse=AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
        return authenticationResponse;
    }
}
