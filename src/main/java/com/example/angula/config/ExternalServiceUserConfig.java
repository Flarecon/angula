package com.example.angula.config;

import com.example.reactor.records.ExternalServiceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableConfigurationProperties(ExternalServiceProperties.class)
@Configuration
public class ExternalServiceUserConfig {

    @Bean
    public InMemoryUserDetailsManager externalServiceUserDetailsService(
            ExternalServiceProperties properties,
            PasswordEncoder encoder
    ) {
        UserDetails prometheus = User.builder()
                .username(properties.prometheus().username())
                .password(encoder.encode(properties.prometheus().password()))
                .roles("PROMETHEUS")
                .build();

        return new InMemoryUserDetailsManager(prometheus);
    }
}
