package com.saadmeddiche.creditmanagement.configurations;

import com.saadmeddiche.creditmanagement.filters.CustomStaticResourceFilter;
import com.saadmeddiche.creditmanagement.properties.CustomStaticResourceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final CustomStaticResourceProperties customStaticResourceProperties;

    @Bean
    public FilterRegistrationBean<CustomStaticResourceFilter> customStaticResourceRegister(){
        FilterRegistrationBean<CustomStaticResourceFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CustomStaticResourceFilter());
        registrationBean.addUrlPatterns(buildUrl(customStaticResourceProperties.getUrl()));

        return registrationBean;
    }

    private String buildUrl(String url) {
        return String.format("/%s/*", url);
    }

}
