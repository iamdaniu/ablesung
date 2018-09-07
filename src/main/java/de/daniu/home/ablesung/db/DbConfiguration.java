package de.daniu.home.ablesung.db;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DbConfiguration {
    private String url;
    private String username;
    private String password;
}
