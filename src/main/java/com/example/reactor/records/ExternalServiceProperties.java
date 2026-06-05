package com.example.reactor.records;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external-service")
public record ExternalServiceProperties(
        User prometheus
) {
    public record User(
            String username,
            String password
    ) {}
}
