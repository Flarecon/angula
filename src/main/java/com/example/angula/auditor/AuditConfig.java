package com.example.angula.auditor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider", dateTimeProviderRef = "dateTimeProvider")
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            String username = "unknown";
            var context = SecurityContextHolder.getContext();
            if (context != null && context.getAuthentication() != null) {
                var auth = context.getAuthentication();
                if (auth.getPrincipal() instanceof UserDetails user) {
                    username = user.getUsername();
                } else if (auth.getPrincipal() instanceof String str) {
                    username = str;
                }
            }
            return Optional.ofNullable(username);
        };
    }


    @Bean
    public DateTimeProvider dateTimeProvider(){
        return ()-> Optional.of(LocalDateTime.now());
    }
}
