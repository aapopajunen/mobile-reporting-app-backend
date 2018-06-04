package com.swp.Security;

import com.swp.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class JwtGenerator {


    private String secret = "verysecretsignature";

    public String generate(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());

        final String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return token;
    }
}
