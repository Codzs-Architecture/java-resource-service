package com.codzs.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * CORS configuration for Resource Service.
 * Minimal configuration since this service should primarily be accessed via BFF.
 */
@Configuration(proxyBeanMethods = false)
public class CorsConfig {

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		
		// Very restrictive headers - only what's needed for API calls
		config.setAllowedHeaders(Arrays.asList(
			"Content-Type", 
			"Authorization"
		));
		
		// Only allow read operations (GET) and preflight (OPTIONS)
		config.setAllowedMethods(Arrays.asList("GET", "HEAD", "OPTIONS"));
		
		// Only allow BFF service origin - no direct frontend access
		config.setAllowedOrigins(Arrays.asList(
			"https://local.codzs.com:8004"  // BFF service only
		));
		
		// Disable credentials (not needed for API-to-API calls)
		config.setAllowCredentials(false);
		
		// Don't expose any headers
		config.setExposedHeaders(Arrays.asList());
		
		// Set max age for preflight cache
		config.setMaxAge(3600L);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}