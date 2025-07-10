// package com.codzs.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// public class ResourceServerWebConfig {
    
//     @Bean
//     @Order(1)
//     public SecurityFilterChain resourceServiceWebSecurityFilterChain(
//             HttpSecurity http) throws Exception {
//         http
//                 .securityMatcher("/articles/**", "/log", "/external-key")
//                 .authorizeHttpRequests(authz -> authz
//                         .requestMatchers("/articles/**")
//                         .hasAuthority("SCOPE_articles.read")
//                         .requestMatchers("/log", "/external-key")
//                         .authenticated()
//                         .anyRequest().authenticated()
//                 )
//                 .oauth2ResourceServer(oauth2 -> oauth2
//                         .jwt(Customizer.withDefaults())
//                 )
//                 .sessionManagement(session -> session
//                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                 )
//                 .csrf(csrf -> csrf.disable());

//         return http.build();
//     }

//     @Bean
//     @Order(2)
//     public SecurityFilterChain managementSecurityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 .securityMatcher("/management/**", "/actuator/**")
//                 .authorizeHttpRequests(authz -> authz
//                         .requestMatchers("/management/**", "/actuator/**").permitAll()
//                         .anyRequest().authenticated()
//                 )
//                 .csrf(csrf -> csrf.disable());

//         return http.build();
//     }
    
//     @Bean
//     @Order(3)
//     public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 .authorizeHttpRequests(authz -> authz
//                         .anyRequest().authenticated()
//                 )
//                 .httpBasic(Customizer.withDefaults())
//                 .csrf(csrf -> csrf.disable());
 
//         return http.build();
//     }
// }
