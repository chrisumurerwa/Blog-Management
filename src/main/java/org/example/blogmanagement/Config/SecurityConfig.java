package org.example.blogmanagement.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //  This configures which URLs are protected or open
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for REST APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // allow register & login
                        .requestMatchers(
                                "/swagger-ui/**", //Swagger UI static resources
                                "/swagger-ui.html", //Swagger UI main page
                                "/v3/api-docs/**",  //OpenAPI documentation JSON
                                "/swagger-resources/**", //JavaScript/CSS libraries used by Swagger
                                "/webjars/**"
                        ).permitAll() // Allow Swagger UI access

                        // Users must provide a valid JWT token to access these endpoints
                        .anyRequest().authenticated() // all other requests require auth
                );

        return http.build();
    }

    //  This exposes AuthenticationManager as a bean
    //    so it can be @Autowired in AuthController
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //  Password encoder used for encoding and matching passwords
    @Bean
    //PasswordEncoder instance using BCrypt algorithm
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
