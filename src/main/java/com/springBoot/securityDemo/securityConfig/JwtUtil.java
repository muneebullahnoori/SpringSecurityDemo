package com.springBoot.securityDemo.securityConfig;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.springBoot.securityDemo.roles.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    private String secret;
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    private List<String> getRolesFromUserDetails(UserDetails userDetails) {
        // Extract roles from UserDetails and convert them to a list of strings
        return userDetails.getAuthorities()
                .stream()
                .map(authority -> {
                    if (authority instanceof Role) {
                        return "ROLE_"+((Role) authority).getRoleName();
                    } else {
                        // Handle other types of GrantedAuthority if necessary
                        return "ROLE_"+ authority.getAuthority();
                    }
                })
                .collect(Collectors.toList());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", getRolesFromUserDetails(userDetails) ); // Add roles to claims getRolesFromUserDetails(userDetails) getRolesFromUserDetails(userDetails)
        return generateToken(claims, userDetails);
    }

//    private List<String> getRolesFromUserDetails(UserDetails userDetails) {
//        // Extract roles from UserDetails and convert them to a list of strings
//        return userDetails.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//    }



    //    public String generateToken(UserDetails userDetails) {
//        return generateToken(new HashMap<>(), userDetails);
//    }
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }
    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parser()
//                .setSigningKey(getSignInKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
        private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
//    public String validateToken(String token){
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secretKey);
//            return JWT.require(algorithm)
//                    .withIssuer("auth-api")
//                    .build()
//                    .verify(token)
//                    .getSubject();
//        } catch (JWTVerificationException exception){
//            System.out.println("catch block!!!!!!!!!!!!!!!!!!!!!!");
//            return "==========================================";
//        }
//    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

