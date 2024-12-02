package org.example.backmobile.Filters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
public class ApiPrefixFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // Vérifie si l'URL commence par /api/v1
        if (!requestURI.startsWith("/api/v1")) {
            // Renvoie une erreur 404 si le préfixe n'est pas présent
            httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Endpoint not found. Please use /api/v1 prefix.");
            return;
        }

        // Continue la chaîne de filtres si le préfixe est présent
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation si nécessaire
    }

    @Override
    public void destroy() {
        // Nettoyage si nécessaire
    }
}
