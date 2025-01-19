package ru.urasha.pointservice.filters;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.urasha.JwtTokenUtil;
import ru.urasha.pointservice.security.CustomAuthentication;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            logger.info("Try to validate token...");

            if (jwtTokenUtil.validateToken(token)) {
                logger.info("Setting security context...");
                setAuthenticationContext(token);
                logger.info("Successfully!");
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("JWT токен невалидный или истекший");
            }
        }

        chain.doFilter(request, response);
    }

    private void setAuthenticationContext(String token) throws JwtException {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        List<GrantedAuthority> authorities = jwtTokenUtil.getRolesFromToken(token);

        CustomAuthentication authentication = new CustomAuthentication(username, userId, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
