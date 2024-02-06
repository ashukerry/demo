package com.example.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import com.example.demo.appuser.AppUserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.userdetails.User;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends AbstractSecurityWebApplicationInitializer {
    // private final AppUserService appUserService;
    // private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/v*/registration/**").permitAll().anyRequest()
                        .authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll())
                // Re-enable CSRF protection for production with proper token handling
                // .csrf();
                .csrf(csrf -> csrf.disable());
        // formLogin((form) -> form
        // .loginPage("/login")
        // .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // i am not using it yet

    @Bean
    public UserDetailsService userDetailsService() {
        // UserDetails user = User.withDefaultPasswordEncoder()
        // .username("user")
        // .password("password")
        // .roles("USER")
        // .build();
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password")) // Use the password encoder
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

}
