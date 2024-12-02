package org.example.backmobile.Filters;

import io.jsonwebtoken.ExpiredJwtException;
import org.example.backmobile.Services.Impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecondaryTokenExpirationFilter extends OncePerRequestFilter {

    private static final String SECONDARY_TOKEN_HEADER = "X-Secondary-Token";
    private final JwtService jwtService;  // Assuming JwtService is used for token validation

    public SecondaryTokenExpirationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/auth/verify/otp")
                || path.startsWith("/auth/authenticate/otp")
                || path.startsWith("/auth/verify/pin")
                || path.startsWith("/user")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/webjars")
                || path.startsWith("/api-docs");
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String secondaryToken = request.getHeader(SECONDARY_TOKEN_HEADER);

        if (secondaryToken == null || jwtService.isTokenExpired(secondaryToken)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Secondary token is missing or expired.");
            return;
        }

        filterChain.doFilter(request, response);
    }

}