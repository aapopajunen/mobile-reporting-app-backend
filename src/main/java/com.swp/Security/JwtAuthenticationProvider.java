package com.swp.Security;

import com.swp.Entity.JwtAuthenticationToken;
import com.swp.Entity.JwtUserDetails;
import com.swp.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtValidator validator;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
        String token = jwtAuthenticationToken.getToken();

        User user = validator.validate(token);

        if (user == null) {
            throw new RuntimeException("JWT SimpleToken is incorrect");
        }

        /*
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(jwtUser.getRole());
        */

        return new JwtUserDetails(user.getUsername(), user.getID(),
                token,
                null);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
