package com.codzs.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {

	@GetMapping("/messages")
	public String[] getMessages() {
		return new String[] {"Message 1", "Message 2", "Message 3"};
	}

	@GetMapping("/user/messages")
	public String[] getUserMessages(@AuthenticationPrincipal Jwt jwt, 
									@RequestParam(value = "use_case", required = false) String use_case) {
		// Add null check and error handling
		if (jwt == null) {
			throw new RuntimeException("JWT token is null - authentication failed");
		}
		
		String subject = jwt.getSubject();
		String username = (subject != null) ? subject : "unknown";
		
		if ("delegation".equals(use_case)) {
			return new String[] {
				"Delegated message 1 for user: " + username,
				"Delegated message 2 for user: " + username,
				"Delegated access granted via token exchange"
			};
		} else if ("impersonation".equals(use_case)) {
			return new String[] {
				"Impersonated message 1 for user: " + username,
				"Impersonated message 2 for user: " + username,
				"Impersonation access granted via token exchange"
			};
		} else {
			return new String[] {
				"User message 1 for: " + username,
				"User message 2 for: " + username,
				"Personal messages for authenticated user"
			};
		}
	}
}
