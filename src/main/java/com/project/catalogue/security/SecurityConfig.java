package com.project.catalogue.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.project.catalogue.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private  UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(
                    httpForm -> {
                        httpForm.loginPage("/").permitAll();
                        httpForm.loginProcessingUrl("/login");
                        httpForm.defaultSuccessUrl("/");
                        httpForm.failureUrl("/error");
                        httpForm.usernameParameter("email");
                        httpForm.passwordParameter("password");
                    }
                )

                .authorizeHttpRequests(registry ->{
                    registry.requestMatchers("/signup","/search-locations","/search-ads", "/error","/assets/**","/images/**","/css/**","/js/**").permitAll();
                    registry.anyRequest().authenticated();
                })
                .build();
    }

}
