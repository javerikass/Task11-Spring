package ru.clevertec.bank.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.clevertec.bank.cache.Cache;
import ru.clevertec.bank.cache.impl.LFUCache;
import ru.clevertec.bank.cache.impl.LRUCache;
import ru.clevertec.bank.printer.impl.PDFprinter;

@Configuration
@PropertySource(value = "classpath:application-test.yml", factory = YamlPropertySourceFactory.class)
public class TestConfig {

    @Bean
    public <K, V> Cache<K, V> LRUCache(@Value("${capacity}") int capacity) {
        return new LRUCache<>(capacity);
    }

    @Bean
    public <K, V> Cache<K, V> LFUCache(@Value("${capacity}") int capacity) {
        return new LFUCache<>(capacity);
    }

    @Bean
    public PDFprinter pdFprinter() {
        return new PDFprinter();
    }

}
