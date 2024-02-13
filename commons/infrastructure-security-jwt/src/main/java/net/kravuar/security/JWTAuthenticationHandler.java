package net.kravuar.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;

@Component
class JWTAuthenticationHandler implements AuthenticationHandler {
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;
    private final JWTProps jwtProps;

    public JWTAuthenticationHandler(Algorithm algorithm, JWTProps jwtProps) {
        this.algorithm = algorithm;
        this.jwtProps = jwtProps;
        this.jwtVerifier = JWT.require(algorithm)
                .withIssuer(jwtProps.getIssuer())
                .build();
    }
    @Override
    public void handleAuthentication(HttpServletRequest request, HttpServletResponse response, IdUserDetails principal) {
        String accessToken = generateToken(getBuilder(principal.getUsername(), jwtProps.getAccessExpiration())
                .withClaim(jwtProps.getIdClaimName(), principal.getId()));
        String refreshToken = generateToken(getBuilder(principal.getUsername(), jwtProps.getRefreshExpiration())
                .withClaim(jwtProps.getIdClaimName(), principal.getId()));
        addCookie(response, jwtProps.getAccessCookieName(), accessToken, (int) jwtProps.getAccessExpiration().getSeconds());
        addCookie(response, jwtProps.getRefreshCookieName(), refreshToken, (int) jwtProps.getRefreshExpiration().getSeconds());
    }

    @Override
    public boolean removeAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(jwtProps.getAccessCookieName()) || cookie.getName().equals(jwtProps.getRefreshCookieName())) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean handleExistingAuthentication(HttpServletRequest request, HttpServletResponse response, IdUserDetails principal) {
        try {
            IdUserDetails existingPrincipal = getAuthentication(request);
            if (existingPrincipal.getId() == principal.getId())
                handleAuthentication(request, response, principal);
            return true;
        } catch (AuthenticationFailedException e) {
            return false;
        }
    }

    @Override
    public IdUserDetails getAuthentication(HttpServletRequest request) throws AuthenticationFailedException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(jwtProps.getAccessCookieName())) {
                    try {
                        String token = cookie.getValue();
                        DecodedJWT decodedJWT = jwtVerifier.verify(token);
                        long id = decodedJWT.getClaim(jwtProps.getIdClaimName()).asLong();
                        String username = decodedJWT.getSubject();
                        return new IdUserDetails(id, username, token, Collections.emptyList());
                    } catch (JWTVerificationException e) {
                        throw new AuthenticationFailedException("Invalid access token");
                    }
                }
            }
        }
        throw new AuthenticationFailedException("Access token not found");
    }

    private JWTCreator.Builder getBuilder(String subject, Duration expiration) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date().toInstant().plus(expiration))
                .withIssuer(jwtProps.getIssuer());
    }

    private String generateToken(JWTCreator.Builder builder) {
        return builder.sign(algorithm);
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeInSeconds);
        response.addCookie(cookie);
    }
}
