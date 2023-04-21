package org.bfsi.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bfsi.auth.IdentityServiceApplication;
import org.bfsi.auth.JWTUtils;
import org.bfsi.auth.config.SecurityConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.bfsi.auth.JWTUtils.populateAuthorities;

public class JWTGeneratorFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LogManager.getLogger(IdentityServiceApplication.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication)
        {
            String userName = authentication.getName();
            String authorities = populateAuthorities(authentication.getAuthorities());
            String jwtAccessToken = JWTUtils.generateAccessToken("JWT Token", userName, authorities);
            String jwtRefreshToken = JWTUtils.generateRefreshToken("JWT Token", userName);

            response.setHeader(SecurityConstants.JWT_HEADER, jwtAccessToken);
            response.setHeader(SecurityConstants.JWT_REFRESH_TOKEN_HEADER, jwtRefreshToken);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/v1/auth/login");
    }
}
