package com.swp.Security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {


                if(e instanceof BadCredentialsException) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "BAD CREDENTIALS");
        } else if(e instanceof InsufficientAuthenticationException) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No rights");
        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
        }
    }
}
