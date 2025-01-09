package com.saadmeddiche.creditmanagement.configurations;

import com.saadmeddiche.creditmanagement.properties.CustomStaticResourceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class CustomStaticResourceConfig implements WebMvcConfigurer {

    private final CustomStaticResourceProperties properties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(buildUrl(properties.getUrl()))
                .addResourceLocations(buildLocation(properties.getPath()));
    }

    private String buildUrl(String url) {
        return String.format("/%s/**", url);
    }

    private String buildLocation(Path path) {
        return String.format("file:%s%s", path.toAbsolutePath(), File.separator);
    }

}
