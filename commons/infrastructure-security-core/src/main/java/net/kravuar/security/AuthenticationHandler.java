package net.kravuar.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationHandler {
    void handleAuthentication(HttpServletRequest request, HttpServletResponse response, IdUserDetails principal);
    boolean removeAuthentication(HttpServletRequest request, HttpServletResponse response);
    boolean handleExistingAuthentication(HttpServletRequest request, HttpServletResponse response, IdUserDetails principal);
    IdUserDetails getAuthentication(HttpServletRequest request) throws AuthenticationFailedException;
}
