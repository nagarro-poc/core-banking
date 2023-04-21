package org.bfsi.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.bfsi.auth.JWTUtils;
import org.bfsi.auth.config.SecurityConstants;
import org.bfsi.auth.payload.request.UserRequestDto;
import org.bfsi.auth.payload.response.JWTResponse;
import org.bfsi.auth.payload.response.ResponseDto;
import org.bfsi.auth.serviceImpl.UserAccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static org.bfsi.auth.JWTUtils.generateAccessToken;
import static org.bfsi.auth.JWTUtils.populateAuthorities;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private UserAccountServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody UserRequestDto user){
        if(userService.findUserByUserName(user.getUserName()).isPresent())
        {
            return getResponseEntityObject(HttpStatus.CONFLICT,
                "User already registered", "{}");
        }
        if (userService.saveUser(user))
        {
            return getResponseEntityObject(HttpStatus.CREATED,
                    "User registered successfully", "{}");
        }
        return getResponseEntityObject(HttpStatus.BAD_REQUEST,
                "User not registered successfully", "{}");
    }


    @GetMapping("/login")
    public ResponseEntity<ResponseDto> login(HttpServletResponse servletResponse) throws JsonProcessingException {
        if(servletResponse.containsHeader(SecurityConstants.JWT_HEADER))
        {
            String token = servletResponse.getHeader(SecurityConstants.JWT_HEADER);
            String refreshToken = servletResponse.getHeader(SecurityConstants.JWT_REFRESH_TOKEN_HEADER);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            JWTResponse jwtResponse = new JWTResponse(authentication.getName(), token, refreshToken);
            return getResponseEntityObject(HttpStatus.OK,
                    "User login successfully", jwtResponse);
        }
        return getResponseEntityObject(HttpStatus.UNAUTHORIZED,
                "User login failed", "{}");

    }

    @GetMapping("/refresh")
    public ResponseEntity<ResponseDto> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader(SecurityConstants.JWT_REFRESH_TOKEN_HEADER);
        if (null != refreshToken) {
            try {
                Claims claims = JWTUtils.getClaims(refreshToken);
                String username = String.valueOf(claims.get("username"));
                String authorities = populateAuthorities(userService.getRolesByUserName(username));

                String newAccessToken = generateAccessToken("JWT Token", username, authorities);

                Long expirationTime = claims.getExpiration().getTime();
                Date expirationDate = new Date(expirationTime);

                response.setHeader(SecurityConstants.JWT_HEADER, newAccessToken);
                response.setHeader(SecurityConstants.JWT_REFRESH_TOKEN_HEADER, refreshToken);
                response.setHeader("Access-Token-Expiration", Long.toString(expirationDate.getTime()));
                JWTResponse jwtResponse = new JWTResponse(username, newAccessToken, refreshToken);
                return getResponseEntityObject(HttpStatus.OK,
                        "Access token generated", jwtResponse);

            } catch (ExpiredJwtException e) {
                return getResponseEntityObject(HttpStatus.FORBIDDEN,
                        e.getMessage()  + " Please login again", "{}");
            } catch (Exception e) {
                return getResponseEntityObject(HttpStatus.FORBIDDEN,
                        e.getMessage(), "{}");
            }
        }
        return getResponseEntityObject(HttpStatus.FORBIDDEN,
                "Refresh header missing or incorrect", "{}");

    }

    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    @GetMapping("/welcome")
    public ResponseEntity<ResponseDto> welcome(){
        return getResponseEntityObject(HttpStatus.OK,
                "Welcome User/Admin to Banking application","{}");
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    public ResponseEntity user(){
        return getResponseEntityObject(HttpStatus.OK,
                "Welcome User to Banking application","{}");
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity admin(){
        return getResponseEntityObject(HttpStatus.OK,
                "Welcome Admin to Banking application","{}");
    }

    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    @GetMapping("/validate")
    public ResponseEntity validate(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication.getName()) {
            return getResponseEntityObject(HttpStatus.OK,
                    "Validation successful", "{}");
        }
        return null;
    }

    private static ResponseEntity<ResponseDto> getResponseEntityObject(HttpStatus status, String msg, Object data) {

        return new ResponseEntity(ResponseDto.builder()
                .statusCode(status.toString())
                .message(msg)
                .data(data)
                .build(), status);
    }
}
