package com.codzs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ResourceServerWebConfig {
    @Bean
    public SecurityFilterChain resourceServiceWebSecurityFilterChain(
            HttpSecurity http) throws Exception {
        http
//                .cors(Customizer.withDefaults())
//                .and()
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/management/**").permitAll()
                        .requestMatchers("/articles/**")
                        .hasAuthority("SCOPE_articles.read")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        return http.build();
    }
}
