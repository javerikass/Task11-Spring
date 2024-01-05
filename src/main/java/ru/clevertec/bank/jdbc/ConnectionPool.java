package ru.clevertec.bank.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConnectionPool {

    private final HikariDataSource dataSource;

    public ConnectionPool(
        @Value("${db.driver}") String dbDriver,
        @Value("${db.url}") String dbUrl,
        @Value("${db.username}") String dbUsername,
        @Value("${db.password}") String dbPassword) {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(dbDriver);
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUsername);
        config.setPassword(dbPassword);

        this.dataSource = new HikariDataSource(config);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

}
