package org.bfsi.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bfsi.auth.JWTUtils;
import org.bfsi.auth.config.SecurityConstants;
import org.bfsi.auth.payload.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTValidatorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getJwtFromRequest(request);

        if (accessToken != null) {
            try {
                Claims claims = JWTUtils.getClaims(accessToken);
                String username = String.valueOf(claims.get("username"));
                String authorities = String.valueOf(claims.get("authorities"));

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(username, null,
                                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (ExpiredJwtException e){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(MediaType.APPLICATION_JSON.toString());
                ResponseDto responseDto = ResponseDto.builder()
                        .statusCode(HttpStatus.FORBIDDEN.toString())
                        .message(e.getMessage())
                        .data("{}")
                        .build();
                response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
                return;
            }
            catch (Exception e){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(MediaType.APPLICATION_JSON.toString());
                ResponseDto responseDto = ResponseDto.builder()
                        .statusCode(HttpStatus.FORBIDDEN.toString())
                        .message(e.getMessage())
                        .data("{}")
                        .build();
                response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
                return;
            }
        }
        filterChain.doFilter(request, response);
     }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstants.JWT_HEADER);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/v1/auth/login")
                || request.getServletPath().equals("/api/v1/auth/refresh");
    }
}