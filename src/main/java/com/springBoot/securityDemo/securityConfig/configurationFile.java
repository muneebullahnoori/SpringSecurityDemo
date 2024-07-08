package com.springBoot.securityDemo.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class configurationFile {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http

                        .csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/add").hasRole("user")
                         .requestMatchers("/user/all").permitAll())
                        .build();
    }
}
