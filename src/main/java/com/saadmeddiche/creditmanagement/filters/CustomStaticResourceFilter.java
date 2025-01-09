package com.saadmeddiche.creditmanagement.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component @Slf4j
public class CustomStaticResourceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String uri = httpServletRequest.getRequestURI();

        Optional<String> fileName = extractFileName(uri);

        if(fileName.isEmpty()) {
            log.warn("Access Denied: No file name found in the request URI");
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if(fileName.get().contains("STRICT-ACCESS")) {
            log.warn("Access Denied To [{}]: File name contains STRICT-ACCESS", fileName.get());
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        log.info("Access Granted to: {}", fileName.get());
        chain.doFilter(request, response);
    }

    private Optional<String> extractFileName(String uri) {
        String[] uriParts = uri.split("/");
        if (uriParts.length > 0) {
            return Optional.of(uriParts[uriParts.length - 1]);
        }
        return Optional.empty();
    }
}
