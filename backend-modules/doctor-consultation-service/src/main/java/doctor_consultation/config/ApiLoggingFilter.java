package doctor_consultation.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

@Component
public class ApiLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ApiLoggingFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        long startTime = System.currentTimeMillis();
        
        // Wrap request and response to enable reading body multiple times
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            // Process the request
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            // Log after request processing
            long duration = System.currentTimeMillis() - startTime;
            logRequest(wrappedRequest);
            logResponse(wrappedResponse, duration);
            
            // Copy response body back to original response
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n=== API Request ===\n");
        logMessage.append("Method: ").append(request.getMethod()).append("\n");
        logMessage.append("URI: ").append(request.getRequestURI()).append("\n");
        
        String queryString = request.getQueryString();
        if (queryString != null) {
            logMessage.append("Query: ").append(queryString).append("\n");
        }
        
        logMessage.append("Remote Address: ").append(request.getRemoteAddr()).append("\n");
        
        // Log headers
        logMessage.append("Headers:\n");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logMessage.append("  ").append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
        }
        
        // Log request body
        byte[] content = request.getContentAsByteArray();
        if (content.length > 0) {
            String body = getContentAsString(content, request.getCharacterEncoding());
            logMessage.append("Body: ").append(body).append("\n");
        }
        
        logMessage.append("===================\n");
        logger.info(logMessage.toString());
    }

    private void logResponse(ContentCachingResponseWrapper response, long duration) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n=== API Response ===\n");
        logMessage.append("Status: ").append(response.getStatus()).append("\n");
        logMessage.append("Duration: ").append(duration).append(" ms\n");
        
        // Log response headers
        logMessage.append("Headers:\n");
        response.getHeaderNames().forEach(headerName -> {
            logMessage.append("  ").append(headerName).append(": ").append(response.getHeader(headerName)).append("\n");
        });
        
        // Log response body
        byte[] content = response.getContentAsByteArray();
        if (content.length > 0) {
            String body = getContentAsString(content, response.getCharacterEncoding());
            logMessage.append("Body: ").append(body).append("\n");
        }
        
        logMessage.append("====================\n");
        logger.info(logMessage.toString());
    }

    private String getContentAsString(byte[] content, String characterEncoding) {
        try {
            return new String(content, characterEncoding != null ? characterEncoding : "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new String(content);
        }
    }
}

