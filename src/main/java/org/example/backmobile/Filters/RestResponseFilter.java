package org.example.backmobile.Filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
@Component
@Order(1)
public class RestResponseFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // Skip filtering for specific URIs
        if (requestURI.startsWith("/api-docs") || requestURI.startsWith("/swagger-ui") || requestURI.equals("/swagger-ui.html")) {
            chain.doFilter(request, response);
            return;
        }

        // Wrap the response to capture the output
        CharResponseWrapper responseWrapper = new CharResponseWrapper(httpResponse);

        // Proceed with the next filter or target resource
        chain.doFilter(request, responseWrapper);

        // Get captured response body as a string
        String responseBody = responseWrapper.getCaptureAsString();

        // Set the content type
        httpResponse.setContentType("application/json");

        // Write the captured response to the original response
        // Make sure to check if the response is committed
        if (!httpResponse.isCommitted()) {
            httpResponse.getWriter().write(responseBody);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }

    // Inner class to capture the response body
    private class CharResponseWrapper extends HttpServletResponseWrapper {
        private StringWriter writer = new StringWriter();
        private PrintWriter printWriter = new PrintWriter(writer);

        public CharResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return printWriter;
        }

        @Override
        public void flushBuffer() throws IOException {
            printWriter.flush(); // Ensure all content is flushed to the writer
            super.flushBuffer();
        }

        public String getCaptureAsString() {
            printWriter.flush(); // Ensure all content is written to the StringWriter
            return writer.toString();
        }
    }
}