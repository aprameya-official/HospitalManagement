package com.example.HospitalManagement.config;

import com.example.HospitalManagement.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    // Inject the custom user details service
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    // Define PasswordEncoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configure AuthenticationManager using AuthenticationConfiguration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Main HttpSecurity configuration
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(auth -> auth
                        .requestMatchers("/login", "/register","/index.html").permitAll()  // Allow login and register pages
//                        .requestMatchers("/admin/**").hasRole("ADMIN")       // Restrict admin paths
                        .anyRequest().authenticated()                        // Require authentication for all other paths
                )
                .formLogin(form -> form
                        .loginPage("/login")                             // Custom login page
                        .defaultSuccessUrl("/", true)                    // Redirect to home on success
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")                            // Logout URL
                        .logoutSuccessUrl("/login?logout")               // Redirect on logout
                        .permitAll()
                );
    }
}