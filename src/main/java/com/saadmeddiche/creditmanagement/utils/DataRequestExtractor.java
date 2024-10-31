package com.saadmeddiche.creditmanagement.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component @RequiredArgsConstructor
public class DataRequestExtractor {

    private final HttpServletRequest request;

    public Map<String, String> pathVariablesBuilder() {

        @SuppressWarnings("unchecked")
        Map<String, String> pathVariables = (Map<String, String>) //Cast Object to Map<String, String>
                request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        return pathVariables;
    }
}
