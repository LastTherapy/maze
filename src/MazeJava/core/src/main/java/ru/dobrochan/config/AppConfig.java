package ru.dobrochan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public PrintSymbolConfig printSymbolConfig() {
        return new PrintSymbolConfig();
    }
}
