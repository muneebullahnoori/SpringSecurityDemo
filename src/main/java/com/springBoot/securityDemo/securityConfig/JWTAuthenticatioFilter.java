package com.springBoot.securityDemo.securityConfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@AllArgsConstructor
@NoArgsConstructor
@Component
public class JWTAuthenticatioFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            String authHeader = request.getHeader("Authorization");
           final String jwtToken;
           final String userEmail;
           if (authHeader == null || !authHeader.startsWith("Bearer ")){
               filterChain.doFilter(request,response);
               return;
           }
           jwtToken = authHeader.substring(7);
            System.out.println("Received token ......"+jwtToken);
//           jwtUtil.validateToken(jwtToken);
           userEmail = jwtUtil.extractUsername(jwtToken);
            System.out.println("extracted userName........"+userEmail);
           if (userEmail != null || SecurityContextHolder.getContext().getAuthentication() == null){
               UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
               if (jwtUtil.isTokenValid(jwtToken, userDetails)) {
                   UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                           userDetails,
                           null,
                           userDetails.getAuthorities());
                   authToken.setDetails(
                           new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(authToken);
                   System.out.println("token is valid and user is authenticated");

               }
           }
           filterChain.doFilter(request,response);
        }
        catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "your token is not valid");
        }
    }
}
