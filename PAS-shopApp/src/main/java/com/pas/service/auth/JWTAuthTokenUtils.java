package com.pas.service.auth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.text.ParseException;
import java.time.LocalDateTime;

public class JWTAuthTokenUtils {

    private static final String SECRET;

    private static final int JWT_TIMEOUT_S = 6000;

    static {
        try {
            SECRET = ((Context) new InitialContext().lookup("java:comp/env")).lookup("SECRET").toString();
        } catch (NamingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String generateJwtString(CredentialValidationResult result) {
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(result.getCallerPrincipal().getName())
                    .claim("auth", String.join(",", result.getCallerGroups()))
                    .issuer("PAS Rest Api")
                    .expirationTime(java.sql.Timestamp.valueOf(LocalDateTime.now().plusSeconds(JWT_TIMEOUT_S)))
                    .build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(new MACSigner(SECRET));
            String tokenString = signedJWT.serialize();
            return signedJWT.serialize();
        } catch (JOSEException e) {
            System.err.println(e.getMessage());
            return "JWT Failure";
        }
    }

    public static boolean validateJwtSignature(String result) {
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(result);
            JWSVerifier verifier = new MACVerifier(SECRET);
            return signedJWT.verify(verifier);
        } catch (JOSEException | ParseException e) {
            return false;
        }
    }
}