package net.kravuar.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTAuthenticationHandler jwtAuthenticationHandler;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            IdUserDetails details = jwtAuthenticationHandler.getAuthentication(request);
            JWTAuthentication authentication = new JWTAuthentication(details);
            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationFailedException ignored) {}
        finally {
            filterChain.doFilter(request, response);
        }
    }
}