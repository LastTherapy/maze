package ru.dobrochan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "print")
@Data
public class PrintSymbolConfig {
    private String emptyCell;
    private String topWall;
    private String rightWall;
    private String bottom;
    private String bottomRight;
    private String leftWall;
    private String rightCell;
}

