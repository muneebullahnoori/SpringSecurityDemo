package com.springBoot.securityDemo.securityConfig;

import com.springBoot.securityDemo.exceptions.customAccessDeniedHandler;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class configurationFile {
    @Autowired
    private JWTAuthenticatioFilter jwtAuthenticatioFilter;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private customAccessDeniedHandler customAccessDeniedHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return
                httpSecurity

                        .csrf(csrf -> csrf.disable())
                        .sessionManagement(session->session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/user/all").hasRole("userCreation")
                        .requestMatchers("/api1").hasRole("userCreation")
                        .anyRequest().permitAll())
                        .addFilterBefore(jwtAuthenticatioFilter,UsernamePasswordAuthenticationFilter.class)
                        .exceptionHandling(exceptionHandling->
                                exceptionHandling.accessDeniedHandler(customAccessDeniedHandler))
                        .build();


    }
}
