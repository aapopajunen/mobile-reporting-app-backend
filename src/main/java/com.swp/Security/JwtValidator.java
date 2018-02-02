package com.swp.Security;

import com.swp.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class JwtValidator {

    private String secret = "verysecretsignature";

    public User validate(String token) {

        User user = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            user = new User();

            user.setUsername(body.getSubject());
            user.setID(Integer.parseInt((String) body.get("userId")));
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return user;
    }
}
