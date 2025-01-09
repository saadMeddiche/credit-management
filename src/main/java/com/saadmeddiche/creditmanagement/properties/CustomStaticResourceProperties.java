package com.saadmeddiche.creditmanagement.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Getter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "custom-static-resource")
public class CustomStaticResourceProperties {

    private String location;

    private String url;

    private Path path;

    public void setLocation(String location) {
        this.location = replacePathSeparator(location);
        this.path = Path.of(location);
    }

    public void setUrl(String url) {
        this.url = replacePathSeparator(url);
    }

    // Remove '/' or '\' from the path
    private String replacePathSeparator(String path) {
        return path.replace("/", "").replace("\\", "");
    }

}
