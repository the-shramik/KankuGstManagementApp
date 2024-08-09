package com.kanku.configuration;

import com.kanku.jwt.JwtAuthenticationEntryPoint;
import com.kanku.jwt.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtRequestFilter;
    private final JwtAuthenticationEntryPoint point;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/api/service/*").permitAll()
                                    .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                    .requestMatchers(
                                            "/product/*",
                                            "/purchase/*",
                                            "/sale/*",
                                            "/supplier/*",
                                            "/user/*"
                                    ).hasAuthority("ADMIN")
                                    .anyRequest()
                                    .authenticated();
                        }
                ).exceptionHandling(ex -> {
                    ex.authenticationEntryPoint(point);
                })
                .sessionManagement(
                        session -> {
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                        }
                ).addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
