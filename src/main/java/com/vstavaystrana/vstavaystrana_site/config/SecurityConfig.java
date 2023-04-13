package com.vstavaystrana.vstavaystrana_site.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Вкл-выкл требования к регистрации. false - только для разработки
    private final boolean enabled = false;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        if(enabled) {
            http
                    .authorizeHttpRequests()
                    .requestMatchers("/login", "/registration").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .permitAll()
                    .and()
                    .logout()
                    .permitAll();
        }
        else{
            http.authorizeHttpRequests().requestMatchers("/**").permitAll();
        }
        return http.build();
    }

}
