package ru.urasha.pointservice.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class CustomAuthentication extends UsernamePasswordAuthenticationToken {
    private final Long userId;

    public CustomAuthentication(String principal, Long userId, Collection<? extends GrantedAuthority> authorities) {
        super(principal, null, authorities);
        this.userId = userId;
    }
}
