package com.findjob.findjobbackend.security.jwt;

import com.findjob.findjobbackend.security.userprincipal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private final String jwtSecret = "bekayrysbaev551@gmail.com";

    public String createToken(Authentication authentication){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        int jwtExpiration = 86400;
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+ jwtExpiration *1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

        public boolean validateToken(String token) {
            try {
                Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
                return true;
            } catch (SignatureException e) {
                logger.error("Invalid JWT signature -> Message: {}", e.getMessage());
            } catch (MalformedJwtException e) {
                logger.error("Invalid format token -> Message: {}", e.getMessage());
            } catch (ExpiredJwtException e) {
                logger.error("Expired JWT token -> Message: {}", e.getMessage());
            } catch (UnsupportedJwtException e) {
                logger.error("Unsupported JWT token -> Message: {}", e.getMessage());
            } catch (IllegalArgumentException e) {
                logger.error("JWT claims string is empty -> Message: {}", e.getMessage());
            }
            return false;
        }

        public String getUerNameFromToken(String token) {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        }
}
