package br.com.wswork.bestcommerceapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;


/*
 * Because it's a test, and it's not explicit which will be the frontends path's (uri plus port, or urls)
 * I've allowed any frontend have access to any endpoint of this application.
 */
@Configuration
public class CorsConfigurations {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Collections.singletonList("*"));

        config.addAllowedMethod("*");

        config.addAllowedHeader("*");

        config.addAllowedHeader("Authorization");

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
