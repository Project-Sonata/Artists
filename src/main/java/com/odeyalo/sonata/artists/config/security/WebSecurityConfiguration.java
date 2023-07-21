package com.odeyalo.sonata.artists.config.security;

import com.odeyalo.suite.security.config.SuiteSecurityAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@Import(SuiteSecurityAutoConfiguration.class)
@EnableWebFluxSecurity
public class WebSecurityConfiguration {
    @Autowired
    Customizer<ServerHttpSecurity.AuthorizeExchangeSpec> authorizeExchangeSpecCustomizer;
    @Autowired
    Customizer<ServerHttpSecurity.ExceptionHandlingSpec> exceptionHandlingSpecCustomizer;
    @Autowired
    Customizer<ServerHttpSecurity.CorsSpec> corsSpecCustomizer;
    @Autowired
    Customizer<ServerHttpSecurity.CsrfSpec> csrfSpecCustomizer;
    @Autowired
    AuthenticationWebFilter authenticationWebFilter;

    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity webSecurity, AuthenticationWebFilter authenticationWebFilter) {
        return webSecurity.csrf(csrfSpecCustomizer)
                .cors(corsSpecCustomizer)
                .exceptionHandling(exceptionHandlingSpecCustomizer)
                .authorizeExchange(authorizeExchangeSpecCustomizer)
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}