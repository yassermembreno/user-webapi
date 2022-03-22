package dev.yrmg.userwebapi.userwebapi.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import dev.yrmg.userwebapi.userwebapi.domain.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import static io.jsonwebtoken.Jwts.parserBuilder;

@Component
public class JwtUtil {
    
    
    private String secretKey;
    @Value("${security.jwt.expiration}")
    private long expirationTime;

    @Autowired
    public JwtUtil(@Value("${security.jwt.secret-key}") String secretKey){        
        this.secretKey =  Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String generateToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);        
        claims.put(this.secretKey,
                roles.stream().map(
                        role ->new SimpleGrantedAuthority(role.getAuthority())
                ).collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationTime))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> getJWsClaims(String token) throws JwtException{
        return parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
    }

    public boolean validateToken(String token) throws JwtException{
        Jws<Claims> claims = getJWsClaims(token);        
        return claims.getBody().getSubject() != null || !claims.getBody().getExpiration().before(new Date());
    }   

    public String getUser(String token) throws JwtException{
        return getJWsClaims(token).getBody().getSubject();  
    }

    @SuppressWarnings(value = "unchecked")
    public List<GrantedAuthority> getRoles(String token) throws JwtException{
        List<Map<String, String>> roleClaims = getJWsClaims(token).getBody().get(secretKey, List.class);
        return roleClaims.stream().map(roleClaim ->
                        new SimpleGrantedAuthority(roleClaim.get("authority")))
                .collect(Collectors.toList());
    }
    
    public Key getSecretKey(){
        return Keys.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8));
    }

   
}
