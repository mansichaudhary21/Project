package com.capg.login.util;

import java.util.Date;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.capg.login.security.services.UserPrinciple;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {
	
	private static final Logger loggerJwtProvider = LoggerFactory.getLogger(JwtProvider.class);
	 
	@Value("jwtSecretValue")
    private String jwtSecret;
 
   
    public String generateJwtToken(Authentication authentication) {
 
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
 
        return Jwts.builder()
                    .setSubject((userPrincipal.getUsername()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
    }
    
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } 
        catch (SignatureException e) {
        	loggerJwtProvider.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
        	loggerJwtProvider.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
        	loggerJwtProvider.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
        	loggerJwtProvider.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
        	loggerJwtProvider.error("JWT claims string is empty -> Message: {}", e);
        }
        
        return false;
    }
    
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                      .setSigningKey(jwtSecret)
                      .parseClaimsJws(token)
                      .getBody().getSubject();
    }

}
