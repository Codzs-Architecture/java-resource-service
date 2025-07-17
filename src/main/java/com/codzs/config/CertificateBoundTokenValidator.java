package com.codzs.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Map;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Validates certificate-bound JWT tokens by verifying the certificate thumbprint
 * matches the cnf claim in the JWT token as per RFC 8705.
 */
public class CertificateBoundTokenValidator implements OAuth2TokenValidator<Jwt> {

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        // Check if token has cnf claim
        Object cnfClaim = jwt.getClaim("cnf");
        if (cnfClaim == null) {
            // No cnf claim means it's a regular token, not certificate-bound
            return OAuth2TokenValidatorResult.success();
        }

        // Extract certificate thumbprint from cnf claim
        String expectedThumbprint = extractThumbprintFromCnfClaim(cnfClaim);
        if (expectedThumbprint == null) {
            return OAuth2TokenValidatorResult.failure(
                new OAuth2Error("invalid_token", "Invalid cnf claim format", null));
        }

        // Get certificate from current request
        X509Certificate certificate = getCurrentRequestCertificate();
        if (certificate == null) {
            return OAuth2TokenValidatorResult.failure(
                new OAuth2Error("invalid_token", "Certificate-bound token requires client certificate", null));
        }

        // Calculate thumbprint of presented certificate
        String actualThumbprint = calculateThumbprint(certificate);
        if (actualThumbprint == null) {
            return OAuth2TokenValidatorResult.failure(
                new OAuth2Error("invalid_token", "Failed to calculate certificate thumbprint", null));
        }

        // Verify thumbprints match
        if (!expectedThumbprint.equals(actualThumbprint)) {
            return OAuth2TokenValidatorResult.failure(
                new OAuth2Error("invalid_token", "Certificate thumbprint mismatch", null));
        }

        return OAuth2TokenValidatorResult.success();
    }

    @SuppressWarnings("unchecked")
    private String extractThumbprintFromCnfClaim(Object cnfClaim) {
        try {
            if (cnfClaim instanceof Map) {
                Map<String, Object> cnfMap = (Map<String, Object>) cnfClaim;
                return (String) cnfMap.get("x5t#S256");
            }
        } catch (Exception e) {
            System.err.println("Failed to extract thumbprint from cnf claim: " + e.getMessage());
        }
        return null;
    }

    private X509Certificate getCurrentRequestCertificate() {
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attrs.getRequest();
            
            // Extract certificate from request attributes
            Object certAttribute = request.getAttribute("jakarta.servlet.request.X509Certificate");
            if (certAttribute instanceof X509Certificate[]) {
                X509Certificate[] certs = (X509Certificate[]) certAttribute;
                return certs.length > 0 ? certs[0] : null;
            }
        } catch (Exception e) {
            System.err.println("Failed to get certificate from request: " + e.getMessage());
        }
        return null;
    }

    private String calculateThumbprint(X509Certificate certificate) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] certBytes = certificate.getEncoded();
            byte[] thumbprintBytes = digest.digest(certBytes);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(thumbprintBytes);
        } catch (NoSuchAlgorithmException | CertificateEncodingException e) {
            System.err.println("Failed to calculate certificate thumbprint: " + e.getMessage());
            return null;
        }
    }
}