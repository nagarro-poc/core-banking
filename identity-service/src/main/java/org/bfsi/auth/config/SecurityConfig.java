package org.bfsi.auth.config;

import org.bfsi.auth.filter.JWTGeneratorFilter;
import org.bfsi.auth.filter.JWTValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.sessionManagement().sessionCreationPolicy((SessionCreationPolicy.STATELESS))
                        .and().csrf().disable()
                .addFilterBefore(new JWTValidatorFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTGeneratorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/welcome", "/api/v1/auth/login",
                        "/api/v1/auth/validate", "/api/v1/auth/admin", "/api/v1/auth/user").authenticated()
                .requestMatchers("/api/v1/auth/register", "/api/v1/auth/refresh",
                        "/v3/api-docs/swagger-config", "/swagger-ui/*", "/v3/api-docs").permitAll()
                .and().httpBasic();

        return http.build();
    }
}
