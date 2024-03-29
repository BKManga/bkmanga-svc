package com.project.graduation.bkmangasvc.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.graduation.bkmangasvc.config.Setting;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenUtil {
    public String generateToken(Authentication authentication) {
        return JWT.create()
                .withSubject(authentication.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + GeneralUtil.getMSFromDays(Setting.TOKEN_EXPIRATION_TIME)))
                .withClaim(
                        "roles",
                        authentication
                                .getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                .sign(getAlgorithm());
    }

    public String verifyToken(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(getAlgorithm()).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getSubject();
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(Setting.TOKEN_SECRET.getBytes());
    }
}
