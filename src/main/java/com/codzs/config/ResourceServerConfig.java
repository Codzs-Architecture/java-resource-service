package com.codzs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class ResourceServerConfig {

	@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
	private String jwkSetUri;
	
	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	private String issuerUri;

	@Bean
	public JwtDecoder jwtDecoder() {
		NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
		
		// Add standard JWT validation
		var defaultValidator = JwtValidators.createDefaultWithIssuer(issuerUri);
		
		// Add certificate-bound token validation
		var certificateBoundValidator = new CertificateBoundTokenValidator();
		
		// Combine validators
		jwtDecoder.setJwtValidator(jwt -> {
			var defaultResult = defaultValidator.validate(jwt);
			if (defaultResult.hasErrors()) {
				return defaultResult;
			}
			return certificateBoundValidator.validate(jwt);
		});
		
		return jwtDecoder;
	}

	@Bean
    @Order(1)
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.securityMatcher("/messages/**", "/user/messages")
				.authorizeHttpRequests(authorize -> authorize
					.requestMatchers("/messages/**").hasAuthority("SCOPE_message.read")
					.requestMatchers("/user/messages").hasAuthority("SCOPE_user.read")
				)
				.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(jwt -> jwt.decoder(jwtDecoder())));
		return http.build();
	}

    @Bean
    @Order(2)
    public SecurityFilterChain managementSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/management/**", "/actuator/**")
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()  // Only health and info are public
                        .requestMatchers("/management/**", "/actuator/**").authenticated()  // All other endpoints require authentication
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())  // Enable HTTP Basic Auth for management endpoints
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}