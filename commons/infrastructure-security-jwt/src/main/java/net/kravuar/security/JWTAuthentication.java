package net.kravuar.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class JWTAuthentication implements Authentication {
    private boolean authenticated;
    private IdUserDetails details;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public JWTAuthentication(IdUserDetails details) {
        this.details = details;
        this.username = details.getUsername();
        this.password = details.getPassword();
        this.authorities = details.getAuthorities();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public String getName() {
        return username;
    }
}
