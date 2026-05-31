package com.genzverse.service;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService 
{
	
	@Value("${jwt.secret}")
	private String secretKey;

	private Key getSigningKey()
	{
	    return Keys.hmacShaKeyFor(
	            secretKey.getBytes()
	    );
	}

    public String generateToken(
            String email,
            String role)
    {
        return Jwts.builder()
                .setSubject(email)

                .claim("role", role)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )
                
                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256
                )

                .compact();
    }
    
    public String extractEmail(String token)
    {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String email)
    {
        String extractedEmail = extractEmail(token);

        return extractedEmail.equals(email)
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token)
    {
        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims extractClaims(String token)
    {
        return Jwts.parserBuilder()
        		.setSigningKey(
        		        getSigningKey()
        		)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}