package ru.clevertec.bank.config;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.clevertec.bank.cache.Cache;
import ru.clevertec.bank.cache.impl.LFUCache;
import ru.clevertec.bank.cache.impl.LRUCache;
import ru.clevertec.bank.jdbc.ConnectionPool;
import ru.clevertec.bank.servlet.listener.InitListener;

@Configuration
@ComponentScan(basePackages = "ru.clevertec.bank")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class AppConfig {

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public InitListener initListener(@Value("${initDB}") String shouldInitDB) {
        return new InitListener(shouldInitDB);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(ConnectionPool connectionPool) {
        return new JdbcTemplate(connectionPool.getDataSource());
    }

    @Bean
    public <K, V> Cache<K, V> cache(@Value("${cache}") String algorithm) {
        if (algorithm != null) {
            if ("LRU".equalsIgnoreCase(algorithm)) {
                return new LRUCache<>();
            } else if ("LFU".equalsIgnoreCase(algorithm)) {
                return new LFUCache<>();
            }
        }
        throw new IllegalArgumentException("Invalid or unspecified cache algorithm");
    }

}
